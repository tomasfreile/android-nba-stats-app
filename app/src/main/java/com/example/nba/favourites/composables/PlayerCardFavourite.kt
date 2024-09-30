package com.example.nba.favourites.composables

import android.content.Context
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
import com.example.nba.R
import com.example.nba.data.Player


@Composable
fun PlayerCardFavourite(player: Player, onClick: () -> Unit) {
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
                Text(text = "Season: " + player.season, color = MaterialTheme.colorScheme.onSurface)
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


private fun getTeamLogoUrl(teamName: String, context: Context): String {
    return when (teamName) {
        context.getString(R.string.Atlanta) -> context.getString(R.string.ATL_URL)
        context.getString(R.string.Boston) -> context.getString(R.string.BOS_URL)
        context.getString(R.string.Brooklyn) -> context.getString(R.string.BKN_URL)
        context.getString(R.string.Charlotte) -> context.getString(R.string.CHA_URL)
        context.getString(R.string.Chicago) -> context.getString(R.string.CHI_URL)
        context.getString(R.string.Cleveland) -> context.getString(R.string.CLE_URL)
        context.getString(R.string.Dallas) -> context.getString(R.string.DAL_URL)
        context.getString(R.string.Denver) -> context.getString(R.string.DEN_URL)
        context.getString(R.string.Detroit) -> context.getString(R.string.DET_URL)
        context.getString(R.string.GoldenState) -> context.getString(R.string.GSW_URL)
        context.getString(R.string.Houston) -> context.getString(R.string.HOU_URL)
        context.getString(R.string.Indiana) -> context.getString(R.string.IND_URL)
        context.getString(R.string.LAClippers) -> context.getString(R.string.LAC_URL)
        context.getString(R.string.LALakers) -> context.getString(R.string.LAL_URL)
        context.getString(R.string.Memphis) -> context.getString(R.string.MEM_URL)
        context.getString(R.string.Miami) -> context.getString(R.string.MIA_URL)
        context.getString(R.string.Milwaukee) -> context.getString(R.string.MIL_URL)
        context.getString(R.string.Minnesota) -> context.getString(R.string.MIN_URL)
        context.getString(R.string.NewOrleans) -> context.getString(R.string.NOP_URL)
        context.getString(R.string.NewYork) -> context.getString(R.string.NYC_URL)
        context.getString(R.string.OklahomaCity) -> context.getString(R.string.OKC_URL)
        context.getString(R.string.Orlando) -> context.getString(R.string.ORL_URL)
        context.getString(R.string.Philadelphia) -> context.getString(R.string.PHI_URL)
        context.getString(R.string.Phoenix) -> context.getString(R.string.PHX_URL)
        context.getString(R.string.Portland) -> context.getString(R.string.POR_URL)
        context.getString(R.string.Sacramento) -> context.getString(R.string.SAC_URL)
        context.getString(R.string.SanAntonio) -> context.getString(R.string.SAS_URL)
        context.getString(R.string.Toronto) -> context.getString(R.string.TOR_URL)
        context.getString(R.string.Utah) -> context.getString(R.string.UTA_URL)
        context.getString(R.string.Washington) -> context.getString(R.string.WAS_URL)
        else -> context.getString(R.string.default_logo)
    }
}


