package com.rodelindev.tvshows.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodelindev.tvshows.navigation.NavigationRoute.Detail
import com.rodelindev.tvshows.navigation.NavigationRoute.Home
import com.rodelindev.tvshows.navigation.NavigationRoute.Profile
import com.rodelindev.tvshows.navigation.NavigationRoute.Splash
import com.rodelindev.tvshows.presentation.detail.DetailScreen
import com.rodelindev.tvshows.presentation.home.HomeScreen
import com.rodelindev.tvshows.presentation.profile.ProfileScreen
import com.rodelindev.tvshows.presentation.splash.SplashScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { - it } },
        popEnterTransition = { slideInHorizontally { - it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        composable<Splash>(
            enterTransition = { slideInVertically() }
        ) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                onClickItem = { tvShow ->
                    navController.navigate(Detail(tvShow.id))
                },
                navigateToProfile = {
                    navController.navigate(Profile)
                }
            )
        }

        composable<Detail> {
            DetailScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}