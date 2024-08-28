package com.example.nba.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomBar(onNavigate: (String) -> Unit) {
    val homeTab = TabBarItem(title = Screen.Home.name, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val favouritesTab = TabBarItem(title = Screen.Favourites.name, selectedIcon = Icons.Filled.Star, unselectedIcon = Icons.Outlined.Star)

    val tabBarItems = listOf(homeTab, favouritesTab)

    TabView(tabBarItems = tabBarItems, onNavigate = onNavigate)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun TabView(tabBarItems: List<TabBarItem>, onNavigate: (String) -> Unit) {
    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ){
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = {
                    selectedTab = index
                    onNavigate(tabBarItem.title)
                },
                icon = {
                       Icon(
                            imageVector = if (selectedTab == index) tabBarItem.selectedIcon else tabBarItem.unselectedIcon,
                            contentDescription = tabBarItem.title
                       )
                },
                label = {
                    Text(text = tabBarItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }
    }
}