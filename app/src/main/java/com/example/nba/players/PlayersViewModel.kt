package com.example.nba.players

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.R
import com.example.nba.apiManager.ApiServiceImpl
import com.example.nba.data.AppDatabase
import com.example.nba.data.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _originalPlayers = MutableStateFlow(listOf<Player>())

    private val _players = MutableStateFlow(listOf<Player>())
    val players: StateFlow<List<Player>> = searchText
        .combine(_players) { text, players ->
            players.filter { player ->
                player.playerName.contains(text, ignoreCase = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _players.value
        )

    private val _loadingPlayers = MutableStateFlow(false)
    val loadingPlayers: StateFlow<Boolean> = _loadingPlayers.asStateFlow()

    private val _showRetryButton = MutableStateFlow(false)
    val showRetryButton: StateFlow<Boolean> = _showRetryButton.asStateFlow()

    private val appDatabase = AppDatabase.getInstance(context)

    init {
        fetchPlayers(context.getString(R.string._2024))
    }

    fun fetchPlayers(season: String) {
        _loadingPlayers.value = true
        viewModelScope.launch {
            val playersFromDB = appDatabase.playerDao().getPlayersBySeason(season)
            if (playersFromDB.isNotEmpty()) {
                _originalPlayers.value = playersFromDB.sortedBy { player -> player.playerName }
                _players.value = _originalPlayers.value
                _loadingPlayers.value = false
            } else {
                apiService.getPlayers(
                    season = season,
                    context = context,
                    onSuccess = {
                        viewModelScope.launch {
                            appDatabase.playerDao().insertPlayers(it)
                            _originalPlayers.value = it.sortedBy { player -> player.playerName }
                            _players.value = _originalPlayers.value
                        }
                        _showRetryButton.value = false
                    },
                    onFail = {
                        _showRetryButton.value = true
                    },
                    loadingFinished = {
                        _loadingPlayers.value = false
                    }
                )
            }
        }
    }

    fun filterByTeams(teams: Set<String>) {
        if (teams.isEmpty()) {
            _players.value = _originalPlayers.value
        } else {
            _players.value = _originalPlayers.value.filter { teams.contains(it.team) }
        }
    }

    fun resetFilters() {
        _players.value = _originalPlayers.value
    }

    fun retryLoadingPlayers(season: String) {
        fetchPlayers(season)
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}
