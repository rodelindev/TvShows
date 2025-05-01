package com.rodelindev.tvshows.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = NavigationRoute.Splash,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { - it } },
        popEnterTransition = { slideInHorizontally { - it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        composable<NavigationRoute.Splash>(
            enterTransition = { slideInVertically() }
        ) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(NavigationRoute.Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavigationRoute.Home> {
            HomeScreen(
                onClickItem = { tvShow ->
                    navController.navigate(
                        NavigationRoute.Detail(tvShow.id)
                    )
                },
                navigateToProfile = {
                    navController.navigate(NavigationRoute.Profile)
                }
            )
        }

        composable<NavigationRoute.Detail> {
            DetailScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<NavigationRoute.Profile> {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}