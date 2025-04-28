package com.rodelindev.tvshows.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodelindev.tvshows.presentation.components.ErrorView
import com.rodelindev.tvshows.presentation.components.LoadingScreen
import com.rodelindev.tvshows.ui.theme.backgroundColor

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            LoadingScreen(
                modifier = Modifier.fillMaxSize()
            )
        }

        state.errorMessage != null -> {
            ErrorView(
                message = state.errorMessage ?: "Unknown error occurred",
                onRetry = { },
                modifier = Modifier.fillMaxSize()
            )
        }

         else -> {
             Scaffold { innerPadding ->
                 state.tvShow?.let { tvShow ->
                     DetailContent(
                         tvShow = tvShow,
                         scrollState = rememberScrollState(),
                         modifier = Modifier
                             .fillMaxSize()
                             .padding(top = innerPadding.calculateTopPadding())
                             .background(color = MaterialTheme.colorScheme.backgroundColor),
                         setAsFavorite = {
                             viewModel.onEvent(DetailEvent.SetAsFavorite)
                         },
                         onBack = onBack
                     )
                 }
             }
         }
    }
}


@Preview
@Composable
fun DetailScreenPreView() {
    DetailScreen(
        onBack = {}
    )
}