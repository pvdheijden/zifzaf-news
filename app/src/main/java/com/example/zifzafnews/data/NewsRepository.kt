package com.example.zifzafnews.data

import javax.inject.Inject

data class NewsItem(
    val title: String,
    val url: String,
    val imageUrl: String
)

class NewsRepository @Inject constructor(
    private val apiService: WebzService
) {
    suspend fun fetchLatestNews(): List<NewsItem> {
        return try {
            val response = apiService.getNews(apiKey = "833d6c04-cf2a-4a23-87fd-df9fa9bc3805")
            response.posts
        } catch (e: Exception) {
            emptyList() // In a real app, handle errors properly!
        }
    }
}