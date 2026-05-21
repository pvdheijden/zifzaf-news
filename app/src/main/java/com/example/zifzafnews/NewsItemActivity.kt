package com.example.zifzafnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.example.zifzafnews.ui.NewsDetailScreen
import com.example.zifzafnews.ui.theme.ZifZafNewsTheme

class NewsItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title") ?: "..."
        val url = intent.getStringExtra("url") ?: "https://www.google.com"
        enableEdgeToEdge()
        setContent {
            ZifZafNewsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            { Text(title) }
                        )
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsDetailScreen(
                        url = url,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

