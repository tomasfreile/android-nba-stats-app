package com.example.nba.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nba.R
import com.example.nba.ui.theme.Blue


@Composable
fun Home() {
    val buttons = listOf<HomeButtonData>(
        HomeButtonData(painterResource(id = R.drawable.basketball_jersey), "Stats by Player", Icons.Filled.KeyboardArrowRight, onClick = {}),
        HomeButtonData(painterResource(id = R.drawable.calendar), "Stats by Season", Icons.Filled.KeyboardArrowRight, onClick = {}),
        HomeButtonData(Icons.Filled.Star, "Favourite Players", Icons.Filled.KeyboardArrowRight, onClick = {}),
        )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.basketball_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(id = R.string.welcome_message),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                buttons.forEach{ button ->
                    ButtonWithIcons(button)
                }
            }
        }
    }
}



@Composable
fun ButtonWithIcons(
    homeButton: HomeButtonData
) {
    Button(onClick = { /*TODO*/ },
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
            )
        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            when (homeButton.leadingIcon) {
                is ImageVector -> Icon(
                    imageVector = homeButton.leadingIcon,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                is Painter -> Icon(
                    painter = homeButton.leadingIcon,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(text = homeButton.text)
            Icon(imageVector = homeButton.trailingIcon, contentDescription = "")
        }

    }
}


data class HomeButtonData(
    val leadingIcon: Any,
    val text: String,
    val trailingIcon: ImageVector,
    val onClick: () -> Unit
)



@Preview
@Composable
fun PreviewHome() {
    Home()
}