package com.example.zifzafnews.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.zifzafnews.NewsItemActivity
import com.example.zifzafnews.data.NewsItem

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val newsItems by viewModel.newsState.collectAsStateWithLifecycle()

    NewsItemsColumn(
        newsItems = newsItems,
        modifier = modifier
    )
}

@Composable
fun NewsItemsColumn(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        items(newsItems) {
                newsItem -> NewsItemCard(newsItem, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun NewsItemCard(
    newsItem: NewsItem,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(onClick = {
        val intent = Intent(context, NewsItemActivity::class.java).apply {
            putExtra("title", newsItem.title)
            putExtra("url", newsItem.url)
        }
        context.startActivity(intent)
    }, modifier = modifier) {
        Column {
            AsyncImage(
                model = newsItem.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = newsItem.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
