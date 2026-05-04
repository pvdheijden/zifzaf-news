package com.example.zifzafnews.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

import com.example.zifzafnews.data.TimeProvider

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val timeProvider: TimeProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(timeProvider.getMinutesInCurrentHour())
    val uiState = _uiState.asStateFlow()

    fun onIncrementClicked() {
        _uiState.update { it + 1 }
    }
}
