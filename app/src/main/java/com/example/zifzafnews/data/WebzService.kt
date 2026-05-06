package com.example.zifzafnews.data

import android.content.Context
import com.example.zifzafnews.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebzSource

// Webz-specific Data Transfer Objects (DTOs)
data class WebzThread(
    val title: String,
    val url: String,
    @SerializedName("main_image") val mainImage: String
)

data class WebzPost(
    val thread: WebzThread
)

data class WebzResponse(
    val posts: List<WebzPost>
)

interface WebzApi {
    @GET("newsApiLite")
    suspend fun getNews(
        @Query("token") apiKey: String,
        @Query("q") query: String
    ): WebzResponse
}

class WebzNews @Inject constructor(
    private val webzApi: WebzApi,
    private val apiKey: String
) : INewsService {
    override suspend fun getNews(query: String): NewsResponse {
        val webzResponse = webzApi.getNews(
            apiKey = apiKey,
            query = query
        )
        
        // Map Webz-specific DTO to domain NewsResponse
        return NewsResponse(
            newsItems = webzResponse.posts.map { post ->
                NewsItem(
                    title = post.thread.title,
                    url = post.thread.url,
                    imageUrl = post.thread.mainImage
                )
            }
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object WebzModule {
    @Provides
//    @WebzApiKey
    fun provideWebzApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.webz_apikey)
    }

    @Provides
    fun provideWebzGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideWebzRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.webz.io/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideWebzApi(retrofit: Retrofit): WebzApi =
        retrofit.create(WebzApi::class.java)

    @Provides
    @WebzSource
    fun provideNewsService(webzNews: WebzNews): INewsService = webzNews
}
