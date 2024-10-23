package com.example.nba

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.nba.navigation.BottomBar
import com.example.nba.navigation.NavHostComposable
import com.example.nba.navigation.TopBar
import com.example.nba.security.AuthFailureScreen
import com.example.nba.security.BiometricAuthManager
import com.example.nba.ui.theme.NBATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val authManager = BiometricAuthManager()
            var isAuthenticated by remember { mutableStateOf(false) }
            var showAuthFailure by remember { mutableStateOf(false) }

            fun retryAuthentication() {
                authManager.authenticate(
                    context = this,
                    onError = {
                        isAuthenticated = false
                        showAuthFailure = true
                        Toast.makeText(this,
                            getString(R.string.there_was_an_error_in_the_authentication), Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = {
                        isAuthenticated = true
                        showAuthFailure = false
                    },
                    onFail = {
                        isAuthenticated = false
                        showAuthFailure = true
                        Toast.makeText(this,
                            getString(R.string.the_authentication_failed_try_again), Toast.LENGTH_SHORT).show()
                    }
                )
            }

            val biometricManager = BiometricManager.from(this)

            val isBiometricAvailable = remember {
                biometricManager.canAuthenticate(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )
            }

            when (isBiometricAvailable) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    if (!isAuthenticated && !showAuthFailure) {
                        retryAuthentication()
                    }
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Toast.makeText(this,
                        stringResource(R.string.biometric_authentication_not_available), Toast.LENGTH_SHORT).show()
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Toast.makeText(this,
                        stringResource(R.string.biometric_hardware_is_unavailable), Toast.LENGTH_SHORT).show()
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Toast.makeText(this,
                        stringResource(R.string.no_biometric_credentials_enrolled), Toast.LENGTH_SHORT).show()
                }
            }

            NBATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (showAuthFailure) {
                        AuthFailureScreen(onRetry = { retryAuthentication() })
                    } else if (isAuthenticated) {
                        Scaffold(
                            topBar = {
                                TopBar(navController = navController)
                            },
                            bottomBar = {
                                BottomBar {
                                    navController.navigate(it)
                                }
                            },
                        ) { innerPadding ->
                            NavHostComposable(innerPadding, navController)
                        }
                    }
                }
            }
        }
    }
}
