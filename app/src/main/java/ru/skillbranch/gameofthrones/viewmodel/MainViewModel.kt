package ru.skillbranch.gameofthrones.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

class MainViewModel : ViewModel(){
    val housesLiveData = MutableLiveData<List<HouseRes>>()



}