package ru.skillbranch.gameofthrones

import android.graphics.Color
import androidx.core.content.ContextCompat

object AppConfig {
    val NEED_HOUSES = arrayOf(
        "House Stark of Winterfell",
        "House Lannister of Casterly Rock",
        "House Targaryen of King's Landing",
        "House Greyjoy of Pyke",
        "House Tyrell of Highgarden",
        "House Baratheon of Dragonstone",
        "House Nymeros Martell of Sunspear"
    )
    const val BASE_URL = "https://www.anapioficeandfire.com/"

    fun getNameByPagerPasition(position: Int): String{
        return when(position) {
            0 -> "Stark"
            1 -> "Lannister"
            2 -> "Targaryen"
            3 -> "Baratheon"
            4 -> "Greyjoy"
            5 -> "Martel"
            else -> "Tyrell"
        }
    }

    fun getColorByPosition(position: Int): Int {
        val context = App.applicationContext()
        return when (position) {
            0 -> ContextCompat.getColor(context, R.color.stark_primary)
            1 -> ContextCompat.getColor(context, R.color.lannister_primary)
            2 -> ContextCompat.getColor(context, R.color.targaryen_primary)
            3 -> ContextCompat.getColor(context, R.color.baratheon_primary)
            4 -> ContextCompat.getColor(context, R.color.greyjoy_primary)
            5 -> ContextCompat.getColor(context, R.color.martel_primary)
            else -> ContextCompat.getColor(context, R.color.tyrel_primary)
        }
    }

    fun getIconByHouseName(name: String): Int {
        return when (name) {
            "Stark" -> R.drawable.stark_icon
            "Lannister" -> R.drawable.lannister_icon
            "Targaryen" -> R.drawable.targaryen_icon
            "Baratheon" -> R.drawable.baratheon_icon
            "Greyjoy" -> R.drawable.greyjoy_icon
            "Martel" -> R.drawable.martel_icon
             else -> R.drawable.tyrel_icon
        }
    }
}