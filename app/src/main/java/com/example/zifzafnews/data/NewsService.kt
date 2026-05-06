package com.example.zifzafnews.data

data class NewsItem(
    val title: String,
    val url: String,
    val imageUrl: String
)

data class NewsResponse(
    val newsItems: List<NewsItem>
)

interface INewsService {
    suspend fun getNews(query: String): NewsResponse
}
