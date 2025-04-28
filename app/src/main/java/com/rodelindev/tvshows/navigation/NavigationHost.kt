package com.rodelindev.tvshows.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.slideInVertically
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
        startDestination = NavigationRoute.Splash
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
                    navController.navigate(
                        NavigationRoute.Detail(tvShow.id)
                    )
                },
                navigateToProfile = {
                    navController.navigate(NavigationRoute.Profile)
                }
            )
        }

        composable<NavigationRoute.Detail>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }

        ) {
            DetailScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<NavigationRoute.Profile>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}