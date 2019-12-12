package ru.skillbranch.gameofthrones

import android.graphics.Color
import androidx.core.content.ContextCompat

object AppConfig {
    val NEED_HOUSES = arrayOf(
        "House Stark of Winterfell",
        "House Lannister of Casterly Rock",
        "House Targaryen of King's Landing",
        "House Baratheon of Dragonstone",
        "House Greyjoy of Pyke",
        "House Nymeros Martell of Sunspear",
        "House Tyrell of Highgarden"


    )
    const val BASE_URL = "https://www.anapioficeandfire.com/"


    fun getColorByHouseName(name: String): Int {
        val context = App.applicationContext()
        return when (name) {
           "Stark" -> ContextCompat.getColor(context, R.color.stark_primary)
           "Lannister" -> ContextCompat.getColor(context, R.color.lannister_primary)
           "Targaryen" -> ContextCompat.getColor(context, R.color.targaryen_primary)
           "Baratheon" -> ContextCompat.getColor(context, R.color.baratheon_primary)
           "Greyjoy" -> ContextCompat.getColor(context, R.color.greyjoy_primary)
           "Martell" -> ContextCompat.getColor(context, R.color.martel_primary)
           else  -> ContextCompat.getColor(context, R.color.tyrel_primary)
        }
    }

    fun getIconByHouseName(name: String): Int {
        return when (name) {
            "Stark" -> R.drawable.stark_icon
            "Lannister" -> R.drawable.lannister_icon
            "Targaryen" -> R.drawable.targaryen_icon
            "Baratheon" -> R.drawable.baratheon_icon
            "Greyjoy" -> R.drawable.greyjoy_icon
            "Martell" -> R.drawable.martel_icon
             else -> R.drawable.tyrel_icon
        }
    }

    fun getBackgroudByHouseName(name: String): Int {
        return when (name) {
            "Stark" -> R.drawable.stark_coast_of_arms
            "Lannister" -> R.drawable.lannister__coast_of_arms
            "Targaryen" -> R.drawable.targaryen_coast_of_arms
            "Baratheon" -> R.drawable.baratheon_coast_of_arms
            "Greyjoy" -> R.drawable.greyjoy_coast_of_arms
            "Martell" -> R.drawable.martel_coast_of_arms
            else -> R.drawable.tyrel_coast_of_arms
        }
    }

    fun getIconColorByHouseName(name: String): Int {
        val context = App.applicationContext()
        return when (name) {
            "Stark" -> ContextCompat.getColor(context, R.color.stark_accent)
            "Lannister" -> ContextCompat.getColor(context, R.color.lannister_accent)
            "Targaryen" -> ContextCompat.getColor(context, R.color.targaryen_accent)
            "Baratheon" -> ContextCompat.getColor(context, R.color.baratheon_accent)
            "Greyjoy" -> ContextCompat.getColor(context, R.color.greyjoy_accent)
            "Martell" -> ContextCompat.getColor(context, R.color.martel_accent)
            else  -> ContextCompat.getColor(context, R.color.tyrel_accent)
        }

    }
}