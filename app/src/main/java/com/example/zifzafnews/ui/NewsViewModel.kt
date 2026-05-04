package com.example.zifzafnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zifzafnews.data.NewsItem
import com.example.zifzafnews.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsState = _newsState.asStateFlow()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            val news = repository.fetchLatestNews()
            _newsState.value = news
        }
    }
}