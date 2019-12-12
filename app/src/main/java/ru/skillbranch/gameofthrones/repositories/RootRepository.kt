package ru.skillbranch.gameofthrones.repositories

import androidx.annotation.VisibleForTesting
import androidx.room.Room
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.App.Companion.applicationContext
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import kotlin.coroutines.CoroutineContext

object RootRepository {

    private const val DB_NAME = "gameOfThrones"
    private var apiFactory = ApiFactory()
    private var api = apiFactory.gameOfThronesApi
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val db: AppDatabase = Room.databaseBuilder(
        applicationContext(),
        AppDatabase::class.java,
        DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()


    /**
     * Получение данных о всех домах из сети
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result: (houses: List<HouseRes>) -> Unit) {
        scope.launch {
            var count = 1
            val houses = ArrayList<HouseRes>()
            while (count < 45) {
                val housesResponse = api.getAllHouses(count, 50).await()
                if (housesResponse.isSuccessful) {
                    val data = housesResponse.body()
                    if (data.isNullOrEmpty()) {
                        break
                    } else {
                        houses.addAll(data)
                    }
                }
                count++
            }
            result.invoke(houses)
        }
    }

    /**
     * Получение данных о требуемых домах по их полным именам из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(
        vararg houseNames: String,
        result: (houses: List<HouseRes>) -> Unit
    ) {
        val houses = ArrayList<HouseRes>()
        val jobs = mutableListOf<Job>()
        scope.launch {
            houseNames.forEach {
                val job = scope.launch {
                    val houseResponse = api.getHouse(it).await()
                    if (houseResponse.isSuccessful) {
                        val data = houseResponse.body()
                        if (!data.isNullOrEmpty()) {
                            houses.addAll(data)
                        }
                    }
                }
                jobs += job
            }
            jobs.joinAll()
            result.invoke(houses)
        }
    }

    suspend fun getCharacter(characterURL: String): CharacterRes? {
        val characterResponse = api.getCharacter(characterURL).await()
        if (characterResponse.isSuccessful) {
            val data = characterResponse.body()
            if (data != null) {
                return data
            }
            return null
        }
        return null
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(
        vararg houseNames: String,
        result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit

    ) {
        val housesWithCharacters = ArrayList<Pair<HouseRes, List<CharacterRes>>>()
        getNeedHouses(*houseNames) {
            scope.launch {
                for (house in it) {
                    val characters = ArrayList<CharacterRes>()
                    for (characterURL in house.swornMembers) {
                        api.getCharacter(characterURL).await().body()
                            ?.let {
                                it.houseId = getHouseShortName(house.name)
                                characters.add(it)
                            }
                    }
                    housesWithCharacters.add(Pair(house, characters))

                }
                result.invoke(housesWithCharacters)
            }

        }
    }


    /**
     * Запись данных о домах в DB
     * @param housesRes - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(housesRes: List<HouseRes>, complete: () -> Unit) {
        val houses = housesRes.map { houseRes ->
            houseRes.toHouse(
                id = houseRes.url.split("/").last(),
                shortName = getHouseShortName(houseRes.name),
                currentLordId = houseRes.currentLord.split("/").last(),
                founderId = houseRes.founder.split("/").last(),
                heirId = houseRes.heir.split("/").last()
            )
        }
        GlobalScope.launch {
            db.gameOfThronesDAO().insertHouses(houses)
            complete.invoke()
        }

    }

    private fun getHouseShortName(name: String): String {
        val split = name.split(" ")
        val index = split.indexOf("of")
        return split[index - 1]
    }

    /**
     * Запись данных о пересонажах в DB
     * @param Characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(charactersRes: List<CharacterRes>, complete: () -> Unit) {
        GlobalScope.launch {
            val characters = charactersRes.map { characterRes ->
                characterRes.toCharacter(
                    id = characterRes.url.split("/").last(),
                    motherId = characterRes.mother.split("/").last(),
                    fatherId = characterRes.father.split("/").last(),
                    spouseId = characterRes.spouse.split("/").last()

                )
            }
            db.gameOfThronesDAO().insertCharacters(characters)
            complete.invoke()
        }

    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        GlobalScope.launch {
            val job = GlobalScope.launch {
                db.gameOfThronesDAO().deleteAllCharacters()
                db.gameOfThronesDAO().deleteAllHouses()
            }
            job.join()
            complete.invoke()
        }
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name: String, result: (characters: List<CharacterItem>) -> Unit) {
        GlobalScope.launch {
            val characters = db.gameOfThronesDAO().getCharactersByHouseName(name)
            result.invoke(characters)
        }
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id: String, result: (character: CharacterFull) -> Unit) {
        GlobalScope.launch {
            val character = db.gameOfThronesDAO().getCharacterById(id)
            val house = db.gameOfThronesDAO().getHouseByCharacterId(id)
            val father =
                if (character.father.isNotEmpty()) db.gameOfThronesDAO().getRelativeCharactersById(
                    character.father
                ) else null
            val mother =
                if (character.mother.isNotEmpty()) db.gameOfThronesDAO().getRelativeCharactersById(
                    character.mother
                ) else null
            val noInfoString =
                applicationContext().resources.getString(R.string.info_is_not_available)
            val characterFull = CharacterFull(
                id = character.id,
                titles = character.titles,
                name = if (character.name.isEmpty()) noInfoString else character.name,
                died = character.died,
                born = character.born,
                aliases = character.aliases,
                house = house.name,
                words = house.words,
                mother = mother,
                father = father
            )
            result.invoke(characterFull)

        }
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed: Boolean) -> Unit) {
        GlobalScope.launch {
            with(db.gameOfThronesDAO()) {
                val isNeeded = getCountOfHouses() == 0 && getCountOfCharacters() == 0
                result.invoke(isNeeded)
            }
        }
    }

    fun getHouses(result: (houses: List<House>) -> Unit) {
        GlobalScope.launch {
            val houses = db.gameOfThronesDAO().getAllHouses()
            result.invoke(houses)
        }
    }

}