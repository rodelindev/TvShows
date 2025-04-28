package com.rodelindev.tvshows.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.rodelindev.tvshows.data.local.entities.TvShowEntity
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.ui.theme.COMMON_PADDING
import com.rodelindev.tvshows.ui.theme.PADDING_8
import com.rodelindev.tvshows.ui.theme.backgroundCardColor
import com.rodelindev.tvshows.ui.theme.textColor
import com.rodelindev.tvshows.utils.constants.HomeConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowItem(
    tvShow: TvShow,
    onClickItem: (TvShow) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = HomeConstants.ITEM_DEFAULT_ELEVATION
        ),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        onClick = { onClickItem(tvShow) },
        modifier = Modifier
            .width(HomeConstants.ITEM_CARD_WIDTH)
            .height(HomeConstants.ITEM_CARD_HEIGHT)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HomeConstants.THUMBNAIL_HEIGHT),
                imageUrl = "${HomeConstants.BASE_URL_IMAGES_THUMBNAIL}${tvShow.posterPath}",
                imageDescriptionResId = null,
                imageDescription = tvShow.name,
                contentScale = ContentScale.Crop
            )
            Text(
                text = tvShow.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = COMMON_PADDING,
                        end = COMMON_PADDING,
                        top = HomeConstants.ITEM_TITLE_TOP_PADDING,
                        bottom = HomeConstants.ITEM_TITLE_BOTTOM_PADDING
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.textColor,
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = COMMON_PADDING, end = COMMON_PADDING, bottom = COMMON_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    rating = tvShow.voteAverage,
                    stars = 5
                )
                Text(
                    text = if (tvShow.voteAverage > HomeConstants.MAXIMUM_AVERAGE) {
                        "${HomeConstants.MAXIMUM_AVERAGE}"
                    } else {
                        "${tvShow.voteAverage}"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.textColor,
                    modifier = Modifier.padding(start = PADDING_8)
                )
            }
        }
    }
}


