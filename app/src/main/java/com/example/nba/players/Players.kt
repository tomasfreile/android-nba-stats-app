package com.example.nba.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nba.R
import com.example.nba.players.composables.DrawerContent
import com.example.nba.players.composables.PlayerCard
import com.example.nba.players.composables.SearchBar
import com.example.nba.players.composables.YearButtonList
import com.example.nba.ui.dimensions.Dimensions
import com.example.nba.ui.theme.Black
import com.example.nba.ui.theme.White
import com.example.nba.utils.generateYearList
import kotlinx.coroutines.launch


@Composable
fun Players(navController: NavHostController) {
    val viewModel = hiltViewModel<PlayersViewModel>()

    val playerList by viewModel.players.collectAsState()
    val loading by viewModel.loadingPlayers.collectAsState()
    val showRetryButton by viewModel.showRetryButton.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val startYear = stringResource(id = R.string.start_year).toInt()
    val endYear = stringResource(id = R.string.end_year).toInt()
    val years = remember { generateYearList(startYear, endYear) }
    val selectedYear = remember { mutableStateOf(years[0]) }

    val selectedTeams = remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(selectedYear.value) {
        viewModel.resetFilters()
        selectedTeams.value = setOf()
        viewModel.fetchPlayers(selectedYear.value)
    }

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                modifier = Modifier.width(Dimensions.drawerWidth),
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topEnd = Dimensions.drawerCornerRadius, bottomEnd = Dimensions.drawerCornerRadius),
            ) {
                DrawerContent(
                    selectedTeams = selectedTeams.value,
                    onTeamToggle = { team ->
                        selectedTeams.value = selectedTeams.value.toggleSelection(team)
                        viewModel.filterByTeams(selectedTeams.value)
                    },
                )
            }
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter_svgrepo_com),
                                contentDescription = stringResource(id = R.string.filter_by_team),
                                modifier = Modifier.size(Dimensions.filterIconSize),
                            )
                        }

                        SearchBar(searchText = searchText, onSearchTextChange = {
                            viewModel.onSearchTextChange(it)
                        })
                    }

                    YearButtonList(listState, years, selectedYear)

                    when {
                        loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(Dimensions.circularProgressSize)
                                        .align(Alignment.Center),
                                    color = White,
                                    trackColor = Black
                                )
                            }
                        }

                        showRetryButton -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(Dimensions.paddingMedium, Alignment.CenterVertically),
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
                        }

                        else -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall),
                                modifier = Modifier
                                    .padding(Dimensions.paddingSmall)
                                    .weight(1f)
                            ) {
                                items(playerList) { player ->
                                    PlayerCard(player = player, onClick = {
                                        navController.navigate("playerDetail/${player.id}")
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


fun Set<String>.toggleSelection(item: String): Set<String> {
    return if (contains(item)) {
        this - item
    } else {
        this + item
    }
}



