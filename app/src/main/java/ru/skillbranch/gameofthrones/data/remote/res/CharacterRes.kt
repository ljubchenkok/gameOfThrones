package ru.skillbranch.gameofthrones.data.remote.res

import com.squareup.moshi.JsonClass
import ru.skillbranch.gameofthrones.data.local.entities.Character

@JsonClass(generateAdapter = true)
data class CharacterRes(
    val url: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String> = listOf(),
    val aliases: List<String> = listOf(),
    val father: String,
    val mother: String,
    val spouse: String,
    val allegiances: List<String> = listOf(),
    val books: List<String> = listOf(),
    val povBooks: List<Any> = listOf(),
    val tvSeries: List<String> = listOf(),
    val playedBy: List<String> = listOf()

){
    fun toCharacter(id: String, houseId: String): Character{
        return Character(
            id = id,
            aliases = this.aliases,
            born = this.born,
            culture = this.culture,
            died = this.died,
            father = this.father,
            gender = this.gender,
            houseId = houseId,
            mother = this.mother,
            name = this.name,
            spouse = this.spouse,
            titles = this.titles

        )
    }
}