package com.example.tablayout.ui.main.Standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class StandingsViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: Intermediate"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}
