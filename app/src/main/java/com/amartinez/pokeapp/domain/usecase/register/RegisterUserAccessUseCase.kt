package com.amartinez.pokeapp.domain.usecase.register

import com.amartinez.pokeapp.data.local.preferences.PreferenceManager
import javax.inject.Inject

class RegisterUserAccessUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(user: String, password: String) {
        preferenceManager.registerUser(user, password)
        preferenceManager.saveFirstAccessCompleted()
    }
}