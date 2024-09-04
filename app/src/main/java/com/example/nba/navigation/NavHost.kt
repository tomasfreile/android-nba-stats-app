package com.example.nba.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nba.home.Home
import com.example.nba.players.Players

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.name,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        composable(Screen.Home.name) {
            Home(navController)
        }
        composable(Screen.Favourites.name) {
            //Players()
        }

        composable(Screen.AllPlayers.name) {
            Players()
        }

    }

}