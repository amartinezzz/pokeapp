package com.amartinez.pokeapp.presentation.screens.login

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amartinez.pokeapp.presentation.components.BodyStructure
import com.amartinez.pokeapp.presentation.utils.BiometricUtils.checkDeviceHasBiometrics
import com.amartinez.pokeapp.presentation.utils.findActivity
import com.amartinez.pokeapp.presentation.viewmodel.login.LogInViewModel

@Composable
fun LogInScreen(
    navToHome: () -> Unit,
    navToRegister: () -> Unit
) {
    val viewModel: LogInViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    val executor = remember(activity) { activity?.let { ContextCompat.getMainExecutor(it) } }
    val biometricPrompt = remember(activity) {
        activity?.let {
            BiometricPrompt(it, executor!!, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    navToHome()
                }
            })
        }
    }

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Welcome Back, Trainer!")
        .setSubtitle("Use your fingerprint to login")
        .setNegativeButtonText("Use Password") // Fallback manual
        .build()

    LaunchedEffect(state.value.isBiometricEnabled) {
        if (state.value.isBiometricEnabled && activity != null && checkDeviceHasBiometrics(activity)) {
            biometricPrompt?.authenticate(promptInfo)
        }
    }

    if (!state.value.isLogInSuccessful) {
        Box(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            BodyStructure {
                LoginView(viewModel, navToRegister)
            }
        }
    } else {
        navToHome()
    }
}

