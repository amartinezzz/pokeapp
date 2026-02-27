package com.amartinez.pokeapp.presentation.screens.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.presentation.components.ButtonComponent
import com.amartinez.pokeapp.presentation.components.TextFieldComponent
import com.amartinez.pokeapp.presentation.viewmodel.login.LogInViewModel
import com.amartinez.pokeapp.ui.theme.DarkGray

@Composable
fun LoginView(viewModel: LogInViewModel, navToRegister: () -> Unit) {
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
            text = stringResource(R.string.sign_in_message),
            style = MaterialTheme.typography.labelMedium,
            color = DarkGray
        )

        TextFieldComponent(
            value = state.value.user,
            onValueChange = viewModel::onEmailChange,
            placeholder = stringResource(R.string.sign_in_user),
            icon = Icons.Default.Email
        )

        TextFieldComponent(
            value = state.value.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = stringResource(R.string.sign_in_password),
            hideText = true,
            icon = Icons.Default.Lock
        )

        val enableButton = state.value.enableButton
        ButtonComponent(
            text = R.string.sign_up_continue,
            enabled = enableButton,
            onClick = viewModel::logIn
        )

        val annotatedText = buildAnnotatedString {
            append(stringResource(R.string.sign_in_register))

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(stringResource(R.string.sign_in_register_highlighted))
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navToRegister()
                },
            text = annotatedText,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal
            ),
            color = DarkGray,
            textAlign = TextAlign.Center
        )
    }
}