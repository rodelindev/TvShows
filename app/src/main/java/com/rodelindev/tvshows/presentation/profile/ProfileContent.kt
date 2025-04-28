package com.rodelindev.tvshows.presentation.profile

import android.R.attr.bottom
import android.R.attr.top
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rodelindev.tvshows.R
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.presentation.components.ActionButton
import com.rodelindev.tvshows.presentation.components.BackButton
import com.rodelindev.tvshows.presentation.components.TvShowItem
import com.rodelindev.tvshows.presentation.profile.components.ProfileTopBar
import com.rodelindev.tvshows.ui.theme.COMMON_PADDING
import com.rodelindev.tvshows.ui.theme.Magnolia
import com.rodelindev.tvshows.ui.theme.Manatee
import com.rodelindev.tvshows.ui.theme.NeonBlue
import com.rodelindev.tvshows.ui.theme.PADDING_24
import com.rodelindev.tvshows.ui.theme.PADDING_32
import com.rodelindev.tvshows.ui.theme.PADDING_8
import com.rodelindev.tvshows.ui.theme.backgroundColor
import com.rodelindev.tvshows.ui.theme.backgroundColorTopBar
import com.rodelindev.tvshows.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    onBack: () -> Unit,
    favorites: List<TvShow>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title) ,
                        color = Magnolia,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    BackButton(
                        onBack = onBack,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.backgroundColorTopBar
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture()
            TextFullCenter(
                textRedId = R.string.user_name,
                textStyle = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.textColor
            )
            TextFullCenter(
                textRedId = R.string.user_media,
                textStyle = MaterialTheme.typography.bodySmall,
                color = Manatee
            )
            FavoritesSection(favorites)
            ActionButton(
                onClick = {},
                buttonTextResId = R.string.log_out,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = COMMON_PADDING,
                        end = COMMON_PADDING,
                        top = PADDING_24,
                        bottom = PADDING_32
                    )
            )
        }
    }
}

@Composable
fun TextFullCenter(
    @StringRes textRedId: Int,
    textStyle: androidx.compose.ui.text.TextStyle,
    color: Color,
) {
    Text(
        text = stringResource(id = textRedId),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color,
        style = textStyle
    )
}

@Composable
fun ProfilePicture() {
    ConstraintLayout(modifier = Modifier.padding(vertical = COMMON_PADDING)) {
        val (ellipse, picture, edit) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_ellipse), contentDescription = null,
            modifier = Modifier.constrainAs(ellipse) {

            })
        Image(
            painter = painterResource(id = R.drawable.photo), contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(72.dp)
                .constrainAs(picture) {
                    start.linkTo(ellipse.start)
                    end.linkTo(ellipse.end)
                    top.linkTo(ellipse.top)
                    bottom.linkTo(ellipse.bottom)
                },
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
                .background(color = NeonBlue)
                .constrainAs(edit) {
                    end.linkTo(parent.end, margin = PADDING_8)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {

                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Edit,
                modifier = Modifier.size(12.dp),
                contentDescription = null,
                tint = Magnolia
            )
        }
    }
}

@Composable
fun FavoritesSection(favorites: List<TvShow>) {
    Text(
        text = stringResource(id = if (favorites.isNotEmpty()) R.string.my_favorites else R.string.no_favorites),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = PADDING_24,
                bottom = COMMON_PADDING,
                start = COMMON_PADDING,
                end = COMMON_PADDING
            ),
        color = MaterialTheme.colorScheme.textColor,
        style = MaterialTheme.typography.titleLarge
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = COMMON_PADDING),
        horizontalArrangement = Arrangement.spacedBy(COMMON_PADDING)
    ) {
        items(favorites) { tvShow ->
            TvShowItem(tvShow = tvShow, onClickItem = {})
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePicturePreview() {
    ProfilePicture()
}