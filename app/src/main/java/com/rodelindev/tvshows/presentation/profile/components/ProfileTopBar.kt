package com.rodelindev.tvshows.presentation.profile.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.rodelindev.tvshows.R
import com.rodelindev.tvshows.presentation.components.BackButton
import com.rodelindev.tvshows.ui.theme.Magnolia
import com.rodelindev.tvshows.ui.theme.PADDING_32
import com.rodelindev.tvshows.ui.theme.backgroundColorTopBar
import com.rodelindev.tvshows.utils.constants.ProfileConstants

@Composable
fun ProfileTopBar(
    onBack: () -> Unit,
    @StringRes titleResId: Int
) {
    val titleTopBar = stringResource(id = titleResId)

    Surface(
        color = MaterialTheme.colorScheme.backgroundColorTopBar,
        modifier = Modifier
            .fillMaxWidth()
            .height(ProfileConstants.DEFAULT_HEIGHT_TOP_BAR)
            .shadow(90.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (back, title) = createRefs()
            BackButton(
                onBack = onBack,
                modifier = Modifier.constrainAs(back) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
            Text(
                text = titleTopBar,
                color = Magnolia,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(back.end, margin = PADDING_32)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}

@Preview
@Composable
fun ProfileTopBarPreview() {
    ProfileTopBar(
        onBack = {},
        titleResId = R.string.profile_title
    )
}