package ru.skillbranch.gameofthrones.repositories

import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface GameOfThronesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouses(vararg : List<House>)

    @Query("DELETE FROM houses")
    fun deleteAllHouses()

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()

    @Query("DELETE FROM character_to_houses")
    fun deleteAllCharacterToHouses()

}