package com.example.nba.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nba.favourites.Favourites
import com.example.nba.home.Home
import com.example.nba.playerDetail.PlayerDetailScreen
import com.example.nba.players.Players
import com.example.nba.ui.dimensions.Dimensions

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.name,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(horizontal = Dimensions.paddingMedium)
    ) {
        composable(Screen.Home.name) {
            Home(navController)
        }

        composable(Screen.AllPlayers.name) {
            Players(navController)
        }

        composable(Screen.Favourites.name) {
            Favourites(navController)
        }

        composable("playerDetail/{playerId}") { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")?.toIntOrNull()
            playerId?.let {
                PlayerDetailScreen(playerId = it, viewModel = hiltViewModel())
            }
        }
    }
}
