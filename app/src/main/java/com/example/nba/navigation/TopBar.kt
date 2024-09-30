package com.example.nba.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nba.R
import com.example.nba.ui.dimensions.Dimensions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBackButton by remember(currentBackStackEntry) {
        derivedStateOf {
            navController.previousBackStackEntry != null && !basePages.contains(navController.currentDestination?.route)
        }
    }

    TopAppBar(
        navigationIcon = if (showBackButton) {
            {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = Dimensions.paddingSmall)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                        ) {
                            navController.popBackStack()
                        }
                )
            }
        } else ({
            // Empty composable when no back button is needed
        }),
        title = {
            Logo()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.nba_big_logo),
        contentDescription = null,
        modifier = Modifier
            .size(Dimensions.logoSize)
            .padding(Dimensions.paddingSmall)
    )
}
