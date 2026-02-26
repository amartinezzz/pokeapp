package com.amartinez.pokeapp.domain.usecase.register

import com.amartinez.pokeapp.data.local.preferences.PreferenceManager
import javax.inject.Inject

class SaveBiometricPreferenceUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(preference: Boolean) {
        preferenceManager.saveBiometricPreference(preference)
    }
}