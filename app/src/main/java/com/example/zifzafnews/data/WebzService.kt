package com.example.zifzafnews.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Type
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class WebzResponse(
    @SerializedName("posts") val posts: List<NewsItem>
)

class NewsItemDeserializer : JsonDeserializer<NewsItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): NewsItem {
        val jsonObject = json.asJsonObject
        val thread = jsonObject.getAsJsonObject("thread")
        
        return NewsItem(
            title = thread.get("title").asString,
            url = thread.get("url").asString,
            imageUrl = thread.get("main_image").asString
        )
    }
}

interface WebzService {
    @GET("newsApiLite")
    suspend fun getNews(
        @Query("token") apiKey: String,
        @Query("q") query: String = "Google topic:\"financial and economic news\" sentiment:negative"
    ): WebzResponse
}

@Module
@InstallIn(SingletonComponent::class)
object WebzModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(NewsItem::class.java, NewsItemDeserializer())
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.webz.io/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideWebzApi(retrofit: Retrofit): WebzService =
        retrofit.create(WebzService::class.java)
}