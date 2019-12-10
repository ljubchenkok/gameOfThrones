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
    fun toCharacter(id: String, houseId: String, motherId: String, fatherId: String, spouseId: String): Character{
        return Character(
            id = id,
            houseId = houseId,
            mother = motherId,
            father = fatherId,
            spouse = spouseId,
            aliases = this.aliases,
            born = this.born,
            culture = this.culture,
            died = this.died,
            gender = this.gender,
            name = this.name,
            titles = this.titles

        )
    }
}