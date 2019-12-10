package ru.skillbranch.gameofthrones.repositories

import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface GameOfThronesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouses(vararg: List<House>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(vararg: List<Character>)

    @Query("SELECT * FROM HOUSES")
    fun getAllHouses(): Array<House>

    @Query("SELECT * FROM HOUSES WHERE name = :name")
    fun getHouse(name: String): House

    @Query("select c.id, c.name, c.titles, c.aliases, h.name as house from  characters c left join houses h on c.houseId = h.id where h.name = :houseName")
    fun getCharactersByHouseName(houseName: String): List<CharacterItem>

    @Query("SELECT count(id) FROM HOUSES")
    fun getCountOfHouses(): Int

    @Query("SELECT count(id) FROM CHARACTERS")
    fun getCountOfCharacters(): Int

    @Query("DELETE FROM houses")
    fun deleteAllHouses()

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()


}