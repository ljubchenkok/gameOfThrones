package ru.skillbranch.gameofthrones.repositories

import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter

@Dao
interface GameOfThronesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouses(vararg: List<House>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(vararg: List<Character>)

    @Query("SELECT * FROM HOUSES")
    fun getAllHouses(): List<House>

    @Query("SELECT * FROM HOUSES WHERE name = :name")
    fun getHouse(name: String): House

    @Query("SELECT * FROM CHARACTERS WHERE id = :id")
    fun getCharacterById(id: String): Character

    @Query("SELECT h.* FROM HOUSES h left join characters c on c.houseId = h.name  WHERE c.id = :id")
    fun getHouseByCharacterId(id: String): House

    @Query("select c.id, c.name, c.titles, c.aliases, c.houseId as house from  characters c where c.houseId = :houseName order by c.name")
    fun getCharactersByHouseName(houseName: String): List<CharacterItem>

    @Query("select c.id, c.name, c.houseId as house from  characters c where c.id = :id")
    fun getRelativeCharactersById(id: String): RelativeCharacter

    @Query("SELECT count(id) FROM HOUSES")
    fun getCountOfHouses(): Int

    @Query("SELECT count(id) FROM CHARACTERS")
    fun getCountOfCharacters(): Int

    @Query("DELETE FROM houses")
    fun deleteAllHouses()

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()




}