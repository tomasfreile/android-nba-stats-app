package com.example.nba.players

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.apiManager.ApiServiceImpl
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
) : ViewModel() {
    private val _players = MutableStateFlow(listOf<Player>())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _loadingPlayers = MutableStateFlow(false)
    val loadingPlayers: StateFlow<Boolean> = _loadingPlayers.asStateFlow()

    private val _showRetryButton = MutableStateFlow(false)
    val showRetryButton: StateFlow<Boolean> = _showRetryButton.asStateFlow()

    init {
        selectPlayersBySeason("2024")
    }
    fun retryLoadingPlayers(season: String) {
        selectPlayersBySeason(season)
    }

     fun selectPlayersBySeason(season: String) {
        _loadingPlayers.value = true
        apiService.getPlayers(
            season = season,
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    _players.value = it.sortedBy { player -> player.playerName }
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