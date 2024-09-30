package com.example.nba.players.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.nba.data.Player
import com.example.nba.utils.getTeamLogoUrl


@Composable
fun PlayerCard(player: Player, onClick: () -> Unit) {
    val context = LocalContext.current

    val teamLogoUrl = getTeamLogoUrl(player.team, context)
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(text = player.playerName, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = "-", color = MaterialTheme.colorScheme.onSurface)
                    Text(text = player.position, color = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = player.age.toString(), color = MaterialTheme.colorScheme.onSurface)
            }
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(teamLogoUrl)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = player.team,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}