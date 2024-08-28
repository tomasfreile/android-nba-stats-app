package com.example.nba.navigation

enum class Screen{
    Home,
    PlayerDetails,
    SeasonLeaders,
    AllPlayers,
    Favourites,
}


val basePages = listOf(
    Screen.Home.name,
)