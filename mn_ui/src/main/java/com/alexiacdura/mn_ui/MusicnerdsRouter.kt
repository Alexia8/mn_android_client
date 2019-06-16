package com.alexiacdura.mn_ui

import androidx.navigation.NavController

class MusicnerdsRouter {
    lateinit var navController: NavController

    fun openFeedFragment(userId: Int) {
        navController.navigate(NavGraphDirections.actionFeedFragment(userId))
    }

    fun openStarredFragment(userId: Int) {
        navController.navigate(NavGraphDirections.actionStarredFragment(userId))
    }

    fun openProfileFragment(userId: Int) {
        navController.navigate(NavGraphDirections.actionProfileFragment(userId))
    }
}