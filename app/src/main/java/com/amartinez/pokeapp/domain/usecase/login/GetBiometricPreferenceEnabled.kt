package com.amartinez.pokeapp.domain.usecase.login

import com.amartinez.pokeapp.data.local.preferences.PreferenceManager
import javax.inject.Inject

class GetBiometricPreferenceEnabled @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(): Boolean =
        preferenceManager.isBiometricPreferenceEnabled()
}