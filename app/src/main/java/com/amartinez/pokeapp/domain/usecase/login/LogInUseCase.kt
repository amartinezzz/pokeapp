package com.amartinez.pokeapp.domain.usecase.login

import com.amartinez.pokeapp.data.local.preferences.PreferenceManager
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(user: String, password: String): Boolean =
        preferenceManager.isUserRegistered(user, password)
}