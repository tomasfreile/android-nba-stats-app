package com.example.nba.navigation

enum class Screen{
    Home,
    PlayerDetail,
    SeasonLeaders,
    AllPlayers,
    Favourites,
}


val basePages = listOf(
    Screen.Home.name,
)