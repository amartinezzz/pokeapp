package com.amartinez.pokeapp.presentation.screens.register

import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amartinez.pokeapp.presentation.components.BodyStructure
import com.amartinez.pokeapp.presentation.utils.BiometricUtils.checkDeviceHasBiometrics
import com.amartinez.pokeapp.presentation.utils.findActivity
import com.amartinez.pokeapp.presentation.viewmodel.register.RegisterViewModel

@Composable
fun RegisterScreen(
    navToHome: () -> Unit
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val executor = remember { activity?.let { ContextCompat.getMainExecutor(it) } }
    val biometricPrompt = remember(activity) {
        activity?.let {
            BiometricPrompt(it, executor!!, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.saveBiometricPreference(true)
                    navToHome()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    viewModel.saveBiometricPreference(false)
                    navToHome()
                }
            })
        }
    }

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Login")
        .setSubtitle("Save your fingerprint for next time")
        .setNegativeButtonText("Skip")
        .build()

    BodyStructure {
        RegisterView(viewModel)
    }

    if (state.value.isUserCreated) {
        if (activity != null && checkDeviceHasBiometrics(activity)) {
            biometricPrompt?.authenticate(promptInfo)
        } else {
            navToHome()
        }
    }
}