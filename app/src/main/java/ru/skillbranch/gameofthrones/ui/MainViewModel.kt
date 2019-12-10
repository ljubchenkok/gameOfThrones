package ru.skillbranch.gameofthrones.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class MainViewModel : ViewModel() {

    val characteItemsLiveData = MutableLiveData<List<CharacterItem>>()


}