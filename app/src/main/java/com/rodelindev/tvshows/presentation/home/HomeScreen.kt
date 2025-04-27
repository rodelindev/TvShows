package com.rodelindev.tvshows.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import com.rodelindev.tvshows.domain.model.getTvShowType
import com.rodelindev.tvshows.presentation.components.ErrorView
import com.rodelindev.tvshows.presentation.home.components.HomeTopBar
import com.rodelindev.tvshows.presentation.home.components.PageLoader
import com.rodelindev.tvshows.ui.theme.TvShowsTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (TvShow) -> Unit,
    navigateToProfile: () -> Unit,
) {
    val moviePagingItems = homeViewModel.tvShowsState.collectAsLazyPagingItems()
    val selectedTvShowFilter = rememberSaveable { mutableStateOf(TvShowFilter.TOP_RATED) }

    LaunchedEffect(key1 = selectedTvShowFilter.value) {
        homeViewModel.onEvent(HomeEvent.ChangeFilter(tvShowFilter = selectedTvShowFilter.value))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(onProfileClick = { navigateToProfile() })
        }
    ) { paddingValue ->

        when {
            moviePagingItems.loadState.refresh is LoadState.Loading -> {
                PageLoader(
                    modifier = Modifier.fillMaxSize()
                )
            }

            moviePagingItems.loadState.hasError -> {
                ErrorView(
                    message = "Unknown error occurred",
                    onRetry = {
                        moviePagingItems.apply {
                            retry()
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            moviePagingItems.loadState.append is LoadState.Error -> {
                ErrorMoreRetry(
                    onRetry = {
                        moviePagingItems.apply {
                            retry()
                        }
                    }
                )
            }

            else -> {
                HomeContent(
                    tvShows = moviePagingItems,
                    onClickItem = onClickItem,
                    selectedTvShowFilter = selectedTvShowFilter.value,
                    onSelectionChange = { tvShowFilterName ->
                        getTvShowType(tvShowFilterName)?.let { tvShowFilter ->
                            selectedTvShowFilter.value = tvShowFilter
                        }
                    },
                    paddingValues = paddingValue
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TvShowsTheme {
        HomeScreen(
            onClickItem = {},
            navigateToProfile = {}
        )
    }
}

