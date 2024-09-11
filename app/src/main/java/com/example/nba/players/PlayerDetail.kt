package com.example.nba.players

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nba.data.Player

@Composable
fun PlayerDetail(playerId: Int, viewModel: PlayersViewModel = hiltViewModel()) {
    val player by viewModel.selectedPlayer.collectAsState()

    LaunchedEffect(playerId) {
        viewModel.getPlayerById(playerId)
    }

    player?.let {
        PlayerDetailContent(player = it)
    } ?: run {
        // Show a loading indicator or error message
        Text(text = "Loading player details...")
    }
}


@Composable
fun PlayerDetailContent(player: Player) {
    Row {
        Text(text = "Name: ${player.playerName}")
        Text(text = "Position: ${player.position}")
        Text(text = "Age: ${player.age}")
        // Add more details as needed
    }
}
