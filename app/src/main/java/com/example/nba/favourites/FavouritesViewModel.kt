package com.example.nba.favourites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavouritesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _favouritePlayers = MutableStateFlow(listOf<Player>())
    val favouritePlayers: StateFlow<List<Player>> = searchText
        .combine(_favouritePlayers) { text, players ->
            players.filter { player ->
                player.playerName.contains(text, ignoreCase = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _favouritePlayers.value
        )


    private val appDatabase = AppDatabase.getInstance(context)

    init {
        fetchPlayers()
    }

    fun fetchPlayers() {
        viewModelScope.launch {
            val favouritePlayers = appDatabase.favoritePlayerDao().getAllFavorites()
            val players = favouritePlayers.mapNotNull { appDatabase.playerDao().getPlayerById(it.playerId) }
            _favouritePlayers.value = players
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}
