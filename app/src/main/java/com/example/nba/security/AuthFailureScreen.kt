package com.example.nba.security

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nba.ui.dimensions.Dimensions
import com.example.nba.R

@Composable
fun AuthFailureScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.paddingLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.authentication_failed),
            fontSize = Dimensions.fontXLarge,
            modifier = Modifier.padding(bottom = Dimensions.paddingLarge),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.please_try_again),
            fontSize = Dimensions.fontMedium,
            modifier = Modifier.padding(bottom = Dimensions.paddingLarge),
            textAlign = TextAlign.Center
        )
        Button(onClick = { onRetry() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}
