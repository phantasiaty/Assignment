package com.example.tablayout.ui.main.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: Advanced"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}
