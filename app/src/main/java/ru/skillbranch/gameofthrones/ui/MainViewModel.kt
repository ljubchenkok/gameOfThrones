package ru.skillbranch.gameofthrones.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainViewModel : ViewModel() {

    val characteItemsLiveData = MutableLiveData<List<CharacterItem>>()


    fun getCharactersByHouseName(name:String){
        RootRepository.findCharactersByHouseName(name){
            characteItemsLiveData.postValue(it)
        }
    }

}