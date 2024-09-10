package com.example.nba.players

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.nba.R
import com.example.nba.ui.theme.Black
import com.example.nba.ui.theme.White


@Composable
fun Players() {
    val viewModel = hiltViewModel<PlayersViewModel>()

    val playerList by viewModel.players.collectAsState()
    val loading by viewModel.loadingPlayers.collectAsState()
    val showRetryButton by viewModel.showRetryButton.collectAsState()

    val years = remember { generateYearList(1993, 2024) }
    val selectedYear = remember { mutableStateOf(years[0]) }

    LaunchedEffect(selectedYear.value) {
        viewModel.selectPlayersBySeason(selectedYear.value)
    }


    if (loading){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(years) { year ->
                YearButton(
                    year = year,
                    isSelected = selectedYear.value == year,
                    onClick = { selectedYear.value = year }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = White,
                trackColor = Black
            )
        }
    } else if (showRetryButton) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_ranking))
            Button(onClick = { viewModel.retryLoadingPlayers(selectedYear.value) }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,

        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items(years) { year ->
                        YearButton(
                            year = year,
                            isSelected = selectedYear.value == year,
                            onClick = { selectedYear.value = year }
                        )
                    }
                }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        items(playerList) { player ->
                            PlayerCard(player = player)
                        }
                    }
                }
            }
    }
}

@Composable
fun YearButton(year: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .border(
                1.dp,
                Black,
                shape = RoundedCornerShape(50)
            ),
        shape = RoundedCornerShape(50),
        color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = year,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun PlayerCard(player: Player) {
    val context = LocalContext.current

    val teamLogoResId = teamLogos[player.team] ?: R.string.default_logo
    val teamLogoUrl = stringResource(id = teamLogoResId)
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()


    Surface(
        modifier = Modifier.fillMaxWidth(),
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
                Text(text = player.playerName, color = MaterialTheme.colorScheme.onSurface)
                Text(text = player.age.toString(), color = MaterialTheme.colorScheme.onSurface)
                Text(text = player.team, color = MaterialTheme.colorScheme.onSurface)
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

fun generateYearList(startYear: Int, endYear: Int): List<String> {
    return (startYear..endYear).map { it.toString() }.reversed()
}

val teamLogos = mapOf(
    "ATL" to R.string.ATL,
    "BOS" to R.string.BOS,
    "BRK" to R.string.BKN,
    "CHO" to R.string.CHA,
    "CHI" to R.string.CHI,
    "CLE" to R.string.CLE,
    "DAL" to R.string.DAL,
    "DEN" to R.string.DEN,
    "DET" to R.string.DET,
    "GSW" to R.string.GSW,
    "HOU" to R.string.HOU,
    "IND" to R.string.IND,
    "LAC" to R.string.LAC,
    "LAL" to R.string.LAL,
    "MEM" to R.string.MEM,
    "MIA" to R.string.MIA,
    "MIL" to R.string.MIL,
    "MIN" to R.string.MIN,
    "NOP" to R.string.NOP,
    "NYK" to R.string.NYC,
    "OKC" to R.string.OKC,
    "ORL" to R.string.ORL,
    "PHI" to R.string.PHI,
    "PHO" to R.string.PHX,
    "POR" to R.string.POR,
    "SAC" to R.string.SAC,
    "SAS" to R.string.SAS,
    "TOR" to R.string.TOR,
    "UTA" to R.string.UTA,
    "WAS" to R.string.WAS
)
