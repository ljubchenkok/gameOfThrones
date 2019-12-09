package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.repositories.Converters

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    @TypeConverters(Converters::class)
    val titles: List<String> = listOf(),
    @TypeConverters(Converters::class)
    val aliases: List<String> = listOf(),
    val father: String, //rel
    val mother: String, //rel
    val spouse: String,
    val houseId: String//rel
)

@Entity(tableName = "character_to_houses")
data class CharacterToHouse(
    @PrimaryKey
    val id: String,
    val characterId: String,
    val houseId: String
)


data class CharacterItem(
    val id: String,
    val house: String, //rel
    val name: String,
    val titles: List<String>,
    val aliases: List<String>
)

data class CharacterFull(
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val house:String, //rel
    val father: RelativeCharacter?,
    val mother: RelativeCharacter?
)

data class RelativeCharacter(
    val id: String,
    val name: String,
    val house:String //rel
)