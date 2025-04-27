package com.rodelindev.tvshows.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoute {

    @Serializable
    data object Splash : NavigationRoute()

    @Serializable
    data object Home : NavigationRoute()

    @Serializable
    data class Detail(val id: Int) : NavigationRoute()

    @Serializable
    data object Profile : NavigationRoute()
}
