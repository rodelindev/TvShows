package com.rodelindev.tvshows.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodelindev.tvshows.presentation.detail.DetailScreen
import com.rodelindev.tvshows.presentation.home.HomeScreen
import com.rodelindev.tvshows.presentation.splash.SplashScreen


@Composable
fun NavigationHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoute.Splash
    ) {
        composable<NavigationRoute.Splash>(
            enterTransition = { slideInVertically() }
        ) {
            SplashScreen(
                navigateToHome = {
                    navHostController.navigate(NavigationRoute.Home) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavigationRoute.Home>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            HomeScreen(
                onClickItem = { tvShow ->
                    navHostController.navigate(
                        NavigationRoute.Detail(tvShow.id)
                    )
                    /*isFavorite = tvShowArgument.isFavorite,*/
                    /*category = tvShowArgument.category,*/
                },
                navigateToProfile = {
                    navHostController.popBackStack()
                    navHostController.navigate(NavigationRoute.Profile)
                }
            )
        }

        composable<NavigationRoute.Detail> {
            DetailScreen(
                onBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}