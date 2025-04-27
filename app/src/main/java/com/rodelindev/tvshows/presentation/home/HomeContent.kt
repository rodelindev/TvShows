package com.rodelindev.tvshows.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.rodelindev.tvshows.R
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import com.rodelindev.tvshows.presentation.components.FilterChips
import com.rodelindev.tvshows.presentation.components.TvShowItem
import com.rodelindev.tvshows.presentation.home.components.PageLoader
import com.rodelindev.tvshows.ui.theme.COMMON_PADDING
import com.rodelindev.tvshows.ui.theme.NeonBlue
import com.rodelindev.tvshows.ui.theme.backgroundColor
import com.rodelindev.tvshows.ui.theme.textColor
import com.rodelindev.tvshows.utils.constants.HomeConstants

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    tvShows: LazyPagingItems<TvShow>,
    onClickItem: (tvShow: TvShow) -> Unit,
    selectedTvShowFilter: TvShowFilter,
    onSelectionChange: (String) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(MaterialTheme.colorScheme.backgroundColor)
    ) {
        FilterChips(
            onSelectionChange = onSelectionChange,
            selectedCTvShowFilter = selectedTvShowFilter
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(HomeConstants.ITEM_CARD_WIDTH),
            contentPadding = PaddingValues(HomeConstants.PADDING_ITEM),
            horizontalArrangement = Arrangement.spacedBy(HomeConstants.SPACING_ITEM),
            verticalArrangement = Arrangement.spacedBy(HomeConstants.SPACING_ITEM),
        ) {
            items(
                count = tvShows.itemCount,
                key = tvShows.itemKey { it.id }
            ) { index ->
                tvShows[index]?.let {
                    TvShowItem(
                        tvShow = it,
                        onClickItem = onClickItem
                    )
                }
            }

            tvShows.apply {
                when {
                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorMoreRetry(
                                onRetry = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            LoadingNextPageItem(modifier = modifier)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item(span = { GridItemSpan(2) }) {
                            ErrorMoreRetry(
                                onRetry = { retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorMoreRetry(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.backgroundColor),
    ) {
        Text(
            text = stringResource(id = R.string.error_fetching_data),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = COMMON_PADDING, end = COMMON_PADDING, top = COMMON_PADDING),
            color = MaterialTheme.colorScheme.textColor,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(COMMON_PADDING),
            /*shape = RoundedCornerShape(6.dp),*/
            contentPadding = PaddingValues(3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonBlue
            )
        ) {
            Text(
                text = stringResource(id = R.string.try_again).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMoreRetryPreview() {
    ErrorMoreRetry(onRetry = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorMoreRetryPreviewDark() {
    ErrorMoreRetry(onRetry = {})
}