package com.amartinez.pokeapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object LogIn

@Serializable
object Register

@Serializable
object Home

@Serializable
object Favorites

@Serializable
data class Detail(val id: Long?)