package com.example.nba.navigation

enum class Screen{
    Home,
    AllPlayers,
    Favourites,
}


val basePages = listOf(
    Screen.Home.name,
    Screen.Favourites.name
)