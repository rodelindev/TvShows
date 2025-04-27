package com.rodelindev.tvshows.presentation.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodelindev.tvshows.R
import com.rodelindev.tvshows.domain.model.Season
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.presentation.components.CoilImage
import com.rodelindev.tvshows.presentation.components.RatingBar
import com.rodelindev.tvshows.presentation.detail.components.DetailTopBar
import com.rodelindev.tvshows.ui.theme.COMMON_PADDING
import com.rodelindev.tvshows.ui.theme.DEFAULT_ELEVATION
import com.rodelindev.tvshows.ui.theme.Magnolia
import com.rodelindev.tvshows.ui.theme.NeonBlue
import com.rodelindev.tvshows.ui.theme.PADDING_24
import com.rodelindev.tvshows.ui.theme.PADDING_4
import com.rodelindev.tvshows.ui.theme.PADDING_8
import com.rodelindev.tvshows.ui.theme.backgroundCardColor
import com.rodelindev.tvshows.ui.theme.textColor
import com.rodelindev.tvshows.utils.constants.DetailConstants
import com.rodelindev.tvshows.utils.constants.DetailConstants.CARD_HEIGHT_ITEM
import com.rodelindev.tvshows.utils.constants.DetailConstants.IMAGE_WIDTH_ITEM
import com.rodelindev.tvshows.utils.constants.DetailConstants.PERCENT_TO_APPEAR_TOP_BAR
import java.math.RoundingMode

@Composable
fun DetailContent(
    tvShow: TvShow,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    setAsFavorite: (isFavorite: Boolean) -> Unit,
    onBack: () -> Unit,
) {
    val maxValue = scrollState.maxValue.toFloat()
    val maxValueSafe = if (maxValue == 0.0f) 1.0f else maxValue
    val percent = (scrollState.value.toFloat() / maxValueSafe) * 100f
    val rounded = percent.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    var showTopBar by rememberSaveable { mutableStateOf(true) }

    if (rounded >= PERCENT_TO_APPEAR_TOP_BAR) {
        showTopBar = true
    }
    if (rounded < PERCENT_TO_APPEAR_TOP_BAR) {
        showTopBar = false
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            DetailHeader(
                titleTvShow = tvShow.name,
                ratingTvShow = tvShow.voteAverage,
                imageUrl = "${DetailConstants.BASE_URL_IMAGES_POSTER}${tvShow.posterPath}",
                onBack = onBack
            )
            tvShow.let {
                DetailSummary(
                    overview = it.overview,
                    isFavorite = it.isFavorite,
                    setAsFavorite = setAsFavorite
                )
            }

            tvShow.seasons.forEach { season ->
                DetailSeasonItem(season = season)
            }
        }
        AnimatedVisibility(
            visible = showTopBar,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            DetailTopBar(title = tvShow.name, onBack = onBack)
        }
    }
}

@Composable
fun DetailHeader(
    titleTvShow: String,
    ratingTvShow: Double,
    imageUrl: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(DetailConstants.HEIGHT_DETAIL_HEADER)
    ) {

        val brush = Brush.verticalGradient(
            listOf(
                Color.Black.copy(alpha = 0.2f),
                Color.Black.copy(alpha = 0.1f),
                Color.Transparent
            ),
            startY = 100.0f,
            endY = 0.0f
        )
        val (
            poster,
            title,
            rating,
            shadow,
            back
        ) = createRefs()

        CoilImage(
            imageUrl = imageUrl,
            imageDescriptionResId = null,
            imageDescription = titleTvShow,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(poster) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                },
            contentScale = ContentScale.FillBounds
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier.constrainAs(back) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }.zIndex(10f),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.DarkGray.copy(alpha = 0.6f)
            ),

        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back),
                tint = Magnolia
            )
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            .background(brush = brush)
            .constrainAs(shadow) {
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom)
            }) { }
        RatingBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(COMMON_PADDING)
                .constrainAs(rating) {
                    bottom.linkTo(parent.bottom)
                    linkTo(parent.start, parent.end)
                },
            rating = ratingTvShow
        )
        Text(
            text = titleTvShow,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = COMMON_PADDING)
                .constrainAs(title) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(rating.top)
                },
            style = MaterialTheme.typography.headlineLarge,
            color = Magnolia
        )
    }
}

@Composable
fun DetailSummary(
    overview: String,
    isFavorite: Boolean,
    setAsFavorite: (isFavorite: Boolean) -> Unit,
) {
    var checked by rememberSaveable { mutableStateOf(isFavorite) }

    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = COMMON_PADDING)
    ConstraintLayout(
        modifier = modifier.padding(top = PADDING_24)
    ) {
        val (summaryTitle, favoriteButton) = createRefs()
        Text(
            text = stringResource(id = R.string.summary),
            color = NeonBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .constrainAs(summaryTitle) {
                    linkTo(parent.start, favoriteButton.start)
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )
        IconButton(
            onClick = {
                checked = !checked
                setAsFavorite(checked)
            },
            modifier = Modifier.constrainAs(favoriteButton) {
                end.linkTo(parent.end)
                linkTo(parent.top, parent.bottom)
            }) {
            val icon = if (checked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
            Icon(icon, contentDescription = null, tint = Red)
        }
    }
    Text(
        text = overview,
        modifier = modifier.padding(top = PADDING_8, bottom = COMMON_PADDING),
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Left,
        color = MaterialTheme.colorScheme.textColor
    )
}

@Composable
fun DetailSeasonItem(
    season: Season,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = DEFAULT_ELEVATION
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = COMMON_PADDING, vertical = PADDING_4)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT_ITEM)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IMAGE_WIDTH_ITEM)
            ) {
                CoilImage(
                    imageUrl = "${DetailConstants.BASE_URL_IMAGES_POSTER}${season.posterPath}",
                    imageDescriptionResId = null,
                    imageDescription = season.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = COMMON_PADDING)
            ) {
                Text(
                    text = season.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = COMMON_PADDING),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                season.episodeCount.let { episodes ->
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.episodes,
                            episodes,
                            episodes
                        ),
                        color = NeonBlue,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = PADDING_8)
                    )
                }
                Text(
                    text = season.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.textColor,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailHeaderPreview() {
    DetailHeader(
        titleTvShow = "The Big Hero 6",
        ratingTvShow = 3.5,
        imageUrl = "",
        onBack = {}
    )
}

@Preview(showBackground = true, heightDp = 600)
@Composable
fun DetailContentPreview() {
    DetailContent(
        tvShow = TvShow(
            backdropPath = "",
            firstAirDate = "",
            id = 0,
            name = "The Big Hero 6",
            originalLanguage = "",
            originalName = "",
            overview = "\"Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 3.5,
            voteCount = 0,
            isFavorite = false,
            category = "",
            seasons = listOf(
                Season(
                    id = 0,
                    name = "Season 1",
                    posterPath = "",
                    episodeCount = 5,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 1,
                    voteAverage = 5.5,
                    airDate = "28-05-2024"
                ),
                Season(
                    id = 0,
                    name = "Season 2",
                    posterPath = "",
                    episodeCount = 9,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 2,
                    voteAverage = 5.0,
                    airDate = "28-05-2024"
                ),
                Season(
                    id = 0,
                    name = "Season 3",
                    posterPath = "",
                    episodeCount = 7,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 3,
                    voteAverage = 5.0,
                    airDate = "28-05-2024"
                )
            )
        ),
        scrollState = rememberScrollState(),
        setAsFavorite = {},
        onBack = {}
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, heightDp = 600)
@Composable
fun DetailContentPreviewDark() {
    DetailContent(
        tvShow = TvShow(
            backdropPath = "",
            firstAirDate = "",
            id = 0,
            name = "The Big Hero 6",
            originalLanguage = "",
            originalName = "",
            overview = "\"Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 3.5,
            voteCount = 0,
            isFavorite = false,
            category = "",
            seasons = listOf(
                Season(
                    id = 0,
                    name = "Season 1",
                    posterPath = "",
                    episodeCount = 5,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 1,
                    voteAverage = 5.0,
                    airDate = "28-05-2024"
                ),
                Season(
                    id = 0,
                    name = "Season 2",
                    posterPath = "",
                    episodeCount = 9,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 2,
                    voteAverage = 5.0,
                    airDate = "28-05-2024"
                ),
                Season(
                    id = 0,
                    name = "Season 3",
                    posterPath = "",
                    episodeCount = 7,
                    overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
                    seasonNumber = 3,
                    voteAverage = 5.0,
                    airDate = "28-05-2024"
                )
            )
        ),
        scrollState = rememberScrollState(),
        setAsFavorite = {},
        onBack = {}
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DetailSeasonItemPreview(
) {
    DetailSeasonItem(
        season = Season(
            id = 0,
            name = "Season 1",
            posterPath = "",
            episodeCount = 5,
            overview = "As the rest of the team face their worst fears, Noddle Burger Boy.",
            seasonNumber = 1,
            voteAverage = 5.0,
            airDate = "28-05-2024"
        )
    )
}
