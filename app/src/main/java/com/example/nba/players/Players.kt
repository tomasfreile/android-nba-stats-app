package com.example.nba.players

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.nba.R
import com.example.nba.data.Player
import com.example.nba.ui.theme.Black
import com.example.nba.ui.theme.White


@Composable
fun Players(navController: NavHostController) {
    val viewModel = hiltViewModel<PlayersViewModel>()

    val playerList by viewModel.players.collectAsState()
    val loading by viewModel.loadingPlayers.collectAsState()
    val showRetryButton by viewModel.showRetryButton.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val startYear = stringResource(id = R.string.start_year).toInt()
    val endYear = stringResource(id = R.string.end_year).toInt()
    val years = remember { generateYearList(startYear, endYear) }
    val selectedYear = remember { mutableStateOf(years[0]) }


    LaunchedEffect(Unit) {
        viewModel.fetchPlayers(selectedYear.value)
    }

    val listState = rememberLazyListState()

    if (loading){
        YearButtonList(listState, years, selectedYear)
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
                SearchBar(searchText = searchText, onSearchTextChange = {
                    viewModel.onSearchTextChange(it)
                })

                YearButtonList(listState, years, selectedYear)
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        items(playerList) { player ->
                            PlayerCard(player = player,onClick = {
                                navController.navigate("playerDetail/${player.id}")
                            })
                        }
                    }
                }
            }
    }
}

@Composable
private fun YearButtonList(
    listState: LazyListState,
    years: List<String>,
    selectedYear: MutableState<String>
) {
    LazyRow(
        state = listState,
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
}


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
            .clickable {
                onClick()
            },
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
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) },
        placeholder = { Text(text = "Search players...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
    )
}

private fun generateYearList(startYear: Int, endYear: Int): List<String> {
    return (startYear..endYear).map { it.toString() }.reversed()
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


@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(searchText = "", onSearchTextChange = {})
}

@Preview
@Composable
fun PreviewYearButton() {
    YearButton(year = "2021", isSelected = false, onClick = {})
}
