package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.repositories.Converters

@Entity(tableName = "houses")
data class House(
    val id: String,
    @PrimaryKey
    val name: String,
    val region: String,
    @ColumnInfo(name = "coat_of_arms")
    val coatOfArms: String,
    val words: String,
    @TypeConverters(Converters::class)
    val titles: List<String>,
    @TypeConverters(Converters::class)
    val seats: List<String>,
    @ColumnInfo(name = "current_lord")
    val currentLord: String, //rel
    val heir: String, //rel
    val overlord: String,
    val founded: String,
    val founder: String, //rel
    @ColumnInfo(name = "died_out")
    val diedOut: String,
    @ColumnInfo(name = "ancestral_weapons")
    val ancestralWeapons: List<String>
)