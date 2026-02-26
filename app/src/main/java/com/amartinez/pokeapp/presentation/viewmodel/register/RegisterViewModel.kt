package com.amartinez.pokeapp.presentation.viewmodel.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amartinez.pokeapp.domain.usecase.register.RegisterUserAccessUseCase
import com.amartinez.pokeapp.domain.usecase.register.SaveBiometricPreferenceUseCase
import com.amartinez.pokeapp.presentation.state.register.RegisterUiState
import com.amartinez.pokeapp.presentation.utils.toSha256
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserAccessUseCase: RegisterUserAccessUseCase,
    private val saveBiometricPreferenceUseCase: SaveBiometricPreferenceUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.update { state ->
            state.copy(
                user = value
            )
        }
        validateFields()
    }

    fun onPasswordChange(value: String) {
        _uiState.update { state ->
            state.copy(
                password = value
            )
        }
        validateFields()
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { state ->
            state.copy(
                confirmPassword = value
            )
        }
        validateFields()
    }

    private fun validateFields() {
        val user = _uiState.value.user
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        val isValid = user.length >= 6 && password.length >= 6 && password == confirmPassword

        _uiState.update {
            it.copy(
                enableButton = isValid,
            )
        }
    }

    fun onRegistrationComplete() {
        viewModelScope.launch {
            registerUserAccessUseCase(
                _uiState.value.user,
                _uiState.value.password.toSha256()
            )

            _uiState.update { state ->
                state.copy(
                    isUserCreated = true
                )
            }
        }
    }

    fun saveBiometricPreference(preference: Boolean) {
        viewModelScope.launch {
            saveBiometricPreferenceUseCase(preference)
        }
    }
}