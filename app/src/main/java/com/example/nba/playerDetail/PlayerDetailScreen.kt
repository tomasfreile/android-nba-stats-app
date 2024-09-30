package com.example.nba.playerDetail

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nba.playerDetail.composables.PlayerDetail

@Composable
fun PlayerDetailScreen(playerId: Int, viewModel: PlayerDetailViewModel = hiltViewModel()) {
    val player by viewModel.selectedPlayer.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(playerId) {
        viewModel.getPlayerById(playerId)
    }

    player?.let {
        PlayerDetail(player = it, isFavorite = isFavorite) {
            viewModel.toggleFavorite(playerId)
        }
    } ?: run {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}
