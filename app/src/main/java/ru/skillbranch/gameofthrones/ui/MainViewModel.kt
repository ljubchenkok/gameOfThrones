package ru.skillbranch.gameofthrones.ui

import androidx.lifecycle.*
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainViewModel : ViewModel() {

    private val query = mutableLiveData("")
    val charactersItemsLiveData = MutableLiveData<List<CharacterItem>>()
    val characterFullLiveData = MutableLiveData<CharacterFull>()
    val housesLiveData = MutableLiveData<List<House>>()


    fun getCharacterItems(): LiveData<List<CharacterItem>> {
        val result = MediatorLiveData<List<CharacterItem>>()
        val filterF = {
            val queryString = query.value!!
            result.value = if (queryString.isEmpty()) charactersItemsLiveData.value
            else charactersItemsLiveData.value?.filter { it.name.contains(queryString, true) }
        }
        result.addSource(charactersItemsLiveData) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }


    fun getCharactersByHouseName(name: String) {
        RootRepository.findCharactersByHouseName(name) {
            charactersItemsLiveData.postValue(it)
        }
    }


    fun getCharacterFullById(id: String) {
        RootRepository.findCharacterFullById(id) {
            characterFullLiveData.postValue(it)
        }
    }

    fun getHouses() {
        RootRepository.getHouses() {
            housesLiveData.postValue(it)
        }
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }


    fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
        val data = MutableLiveData<T>()
        if (defaultValue != null) {
            data.value = defaultValue
        }
        return data
    }
}