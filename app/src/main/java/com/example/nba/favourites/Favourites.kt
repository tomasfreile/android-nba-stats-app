package com.example.nba.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nba.R
import com.example.nba.favourites.composables.PlayerCardFavourite
import com.example.nba.ui.dimensions.Dimensions


@Composable
fun Favourites(navController: NavHostController) {
    val viewModel = hiltViewModel<FavouritesViewModel>()

    val playerList by viewModel.favouritePlayers.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (playerList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_favourites),
                    fontWeight = FontWeight.Bold,
                    )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall),
                ) {
                items(playerList) { player ->
                    PlayerCardFavourite(player = player, onClick = {
                        navController.navigate("playerDetail/${player.id}")
                    })
                }
            }
        }
    }
}
