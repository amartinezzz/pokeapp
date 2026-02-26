package com.amartinez.pokeapp.presentation.state.register

data class RegisterUiState(
    val user: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val enableButton: Boolean = false,
    val isUserCreated: Boolean = false
)