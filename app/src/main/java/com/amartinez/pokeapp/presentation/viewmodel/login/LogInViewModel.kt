package com.amartinez.pokeapp.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amartinez.pokeapp.domain.usecase.login.GetBiometricPreferenceEnabled
import com.amartinez.pokeapp.domain.usecase.login.LogInUseCase
import com.amartinez.pokeapp.presentation.state.login.LogInUiState
import com.amartinez.pokeapp.presentation.utils.toSha256
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val signUseCase: LogInUseCase,
    private val getBiometricPreferenceEnabled: GetBiometricPreferenceEnabled
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState: StateFlow<LogInUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            val isBiometricEnabled = getBiometricPreferenceEnabled()
            _uiState.update { state ->
                state.copy(
                    isBiometricEnabled = isBiometricEnabled
                )
            }
        }
    }

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

    private fun validateFields() {
        val user = _uiState.value.user
        val password = _uiState.value.password

        val isValid = user.length >= 6 && password.length >= 6

        _uiState.update {
            it.copy(
                enableButton = isValid,
            )
        }
    }

    fun logIn() {
        viewModelScope.launch {
            val user = _uiState.value.user
            val password = _uiState.value.password

            val isLogInSuccessful = signUseCase(user, password.toSha256())
            _uiState.update {
                it.copy(
                    isLogInSuccessful = isLogInSuccessful,
                )
            }
        }
    }
}