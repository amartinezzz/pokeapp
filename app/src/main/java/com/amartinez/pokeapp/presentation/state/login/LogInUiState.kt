package com.amartinez.pokeapp.presentation.state.login

data class LogInUiState(
    val user: String = "",
    val password: String = "",
    val enableButton: Boolean = false,
    val isLogInSuccessful: Boolean = false,
    val isBiometricEnabled: Boolean = false
)