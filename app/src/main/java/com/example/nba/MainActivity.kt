package com.example.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nba.home.Home
import com.example.nba.navigation.BottomBar
import com.example.nba.navigation.NavHostComposable
import com.example.nba.navigation.TopBar
import com.example.nba.ui.theme.NBATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NBATheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   Scaffold (
                       topBar = {
                           TopBar(
                                navController = navController,
                           )
                       },
                       bottomBar = {
                           BottomBar {
                               navController.navigate(it)
                           }
                       },
                   ){ innerPadding ->
                          NavHostComposable(innerPadding, navController)
                   }

               }
            }
        }
    }
}



