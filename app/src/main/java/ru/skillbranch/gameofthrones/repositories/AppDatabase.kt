package ru.skillbranch.gameofthrones.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House

@Database(entities = [House::class, Character::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameOfThronesDAO(): GameOfThronesDAO
}