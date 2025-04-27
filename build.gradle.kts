// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlinx.compose.compiler) apply false
    //KSP
    alias(libs.plugins.ksp) apply false
    //Dagger Hilt
    alias(libs.plugins.dagger.hilt.android) apply false
}