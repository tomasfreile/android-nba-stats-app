package com.example.nba.playerDetail

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlayerDetailScreen(playerId: Int, viewModel: PlayerDetailViewModel = hiltViewModel()) {
    val player by viewModel.selectedPlayer.collectAsState()

    LaunchedEffect(playerId) {
        viewModel.getPlayerById(playerId)
    }

    player?.let {
        PlayerDetail(player = it)
    } ?: run {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}
