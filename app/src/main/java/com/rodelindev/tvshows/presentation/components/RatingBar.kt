package com.rodelindev.tvshows.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodelindev.tvshows.R
import com.rodelindev.tvshows.ui.theme.IrisBlue
import com.rodelindev.tvshows.utils.constants.HomeConstants
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = IrisBlue,
) {
    val voteAverage = if (rating > HomeConstants.MAXIMUM_AVERAGE) HomeConstants.MAXIMUM_AVERAGE else rating
    val filledStars = floor(voteAverage).toInt()
    val unfilledStars = (stars - ceil(voteAverage)).toInt()
    val halfStar = !(voteAverage.rem(1).equals(0.0))
    val modifierStar = Modifier.size(16.dp)

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = modifierStar
            )
        }

        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = starsColor,
                modifier = modifierStar
            )
        }

        repeat(unfilledStars) {
            Icon(
                painter = painterResource(R.drawable.star_outline),
                contentDescription = null,
                tint = starsColor,
                modifier = modifierStar
            )
        }
    }
}


@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 2.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    RatingBar(rating = 5.0)
}

@Preview
@Composable
fun RatingPreviewWorst() {
    RatingBar(rating = 1.0)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBar(rating = 0.0, starsColor = Color.Gray)
}