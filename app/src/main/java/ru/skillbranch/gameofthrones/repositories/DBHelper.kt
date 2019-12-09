package ru.skillbranch.gameofthrones.repositories

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.data.local.entities.House

class DBHelper(context: Context) {
    private val db: AppDatabase
    companion object {
        private const val DB_NAME = "gameOfThrones"
    }

    init {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun insertHouses(
        houses: List<House>,
        complete: () -> Unit
    ){
        GlobalScope.launch {
            db.gameOfThronesDAO().insertHouses(houses)
        }
        complete.invoke()
    }

    fun deleteAll(){
        GlobalScope.launch {
            db.gameOfThronesDAO().deleteAllCharacterToHouses()
            db.gameOfThronesDAO().deleteAllCharacters()
            db.gameOfThronesDAO().deleteAllHouses()

        }
    }

}