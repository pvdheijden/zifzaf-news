package com.example.zifzafnews.data

import javax.inject.Inject


class NewsRepository @Inject constructor(
    @WebzSource private val apiService: INewsService
) {
    suspend fun fetchLatestNews(): List<NewsItem> {
        return try {
            val response = apiService.getNews("Hacking and dark web news")
            response.newsItems
        } catch (e: Exception) {
            emptyList() // In a real app, handle errors properly!
        }
    }
}