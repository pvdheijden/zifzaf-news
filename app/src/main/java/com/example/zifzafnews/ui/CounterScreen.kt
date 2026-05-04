package com.example.zifzafnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zifzafnews.ui.theme.CounterTheme

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = hiltViewModel()
) {
    val count by viewModel.uiState.collectAsStateWithLifecycle()

    CounterContent(
        count = count,
        onIncrement = {
            viewModel.onIncrementClicked()
        },
        modifier = modifier
    )
}

@Composable
fun CounterContent(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Count is: $count")
        Button(onClick = onIncrement) {
            Text("Increment")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    CounterTheme {
        CounterContent(
            count = 12,
            onIncrement = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
