package com.amartinez.pokeapp.presentation.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.presentation.components.ButtonComponent
import com.amartinez.pokeapp.presentation.components.TextFieldComponent
import com.amartinez.pokeapp.presentation.viewmodel.register.RegisterViewModel
import com.amartinez.pokeapp.ui.theme.DarkGray

@Composable
fun RegisterView(
    viewModel: RegisterViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.sign_up_message),
            style = MaterialTheme.typography.labelMedium,
            color = DarkGray
        )

        TextFieldComponent(
            value = state.value.user,
            onValueChange = viewModel::onEmailChange,
            placeholder = stringResource(R.string.sign_up_user),
            icon = Icons.Default.Email
        )

        TextFieldComponent(
            value = state.value.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = stringResource(R.string.sign_up_password),
            hideText = true,
            icon = Icons.Default.Lock
        )

        TextFieldComponent(
            value = state.value.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            placeholder = stringResource(R.string.sign_up_confirm_password),
            hideText = true,
            icon = Icons.Default.Done
        )

        val enableButton = state.value.enableButton
        ButtonComponent(
            text = R.string.sign_up_continue,
            enabled = enableButton,
            onClick = viewModel::onRegistrationComplete
        )
    }
}