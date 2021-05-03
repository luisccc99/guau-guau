package com.example.guau_guau.ui.base

import androidx.lifecycle.ViewModel
import com.example.guau_guau.data.repositories.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
    ) : ViewModel() {

}