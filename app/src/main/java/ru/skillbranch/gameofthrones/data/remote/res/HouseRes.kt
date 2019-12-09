package ru.skillbranch.gameofthrones.data.remote.res

import com.squareup.moshi.JsonClass
import ru.skillbranch.gameofthrones.data.local.entities.House

@JsonClass(generateAdapter = true)
data class HouseRes(
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String> = listOf(),
    val seats: List<String> = listOf(),
    val currentLord: String,
    val heir: String,
    val overlord: String,
    val founded: String,
    val founder: String,
    val diedOut: String,
    val ancestralWeapons: List<String> = listOf(),
    val cadetBranches: List<Any> = listOf(),
    val swornMembers: List<String> = listOf()
){
    fun toHouse(id: String): House{
        return House(
            id = id,
            name = this.name,
            region = this.region,
            coatOfArms = this.coatOfArms,
            words = this.words,
            titles = this.titles,
            seats = this.seats,
            currentLord = this.currentLord,
            heir = this.heir,
            overlord = this.overlord,
            founded = this.founded,
            founder = this.founder,
            diedOut = this.diedOut,
            ancestralWeapons = this.ancestralWeapons
        )

    }
}