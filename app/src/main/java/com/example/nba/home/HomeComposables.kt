package com.example.nba.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nba.R

@Composable
fun Home() {
    val buttons = listOf(
        HomeButtonData(
            leadingIcon = painterResource(id = R.drawable.leaderboard),
            text = "Season Leaders",
            trailingIcon = Icons.Filled.KeyboardArrowRight,
            onClick = {}
        ),
        HomeButtonData(
            leadingIcon = painterResource(id = R.drawable.basketball_jersey),
            text = "All Players",
            trailingIcon = Icons.Filled.KeyboardArrowRight,
            onClick = {}
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderSection()
                ButtonSection(buttons)
            }
            
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Logo()
        WelcomeMessage()
    }
}

@Composable
private fun WelcomeMessage() {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.nba_big_logo),
        contentDescription = null,
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp)
    )
}

@Composable
fun ButtonSection(buttons: List<HomeButtonData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .border(
                width = 1.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        buttons.forEachIndexed { index, button ->
            val shape = when (index) {
                0 -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                buttons.size - 1 -> RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                else -> RectangleShape
            }

            ButtonWithIcons(button, shape)


        }
    }
}

@Composable
fun ButtonWithIcons(homeButton: HomeButtonData, shape: Shape) {
    Button(
        onClick = { homeButton.onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = shape // Use the passed shape
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Icon(
                painter = homeButton.leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )
            Text(
                text = homeButton.text,
                style = MaterialTheme.typography.bodyLarge,
            )
            Icon(
                imageVector = homeButton.trailingIcon,
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )
        }
    }
}

data class HomeButtonData(
    val leadingIcon: Painter,
    val text: String,
    val trailingIcon: ImageVector,
    val onClick: () -> Unit
)

@Preview
@Composable
fun PreviewHome() {
    Home()
}
