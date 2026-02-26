package com.amartinez.pokeapp.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentActivity
import com.amartinez.pokeapp.ui.theme.TypeBug
import com.amartinez.pokeapp.ui.theme.TypeDark
import com.amartinez.pokeapp.ui.theme.TypeDragon
import com.amartinez.pokeapp.ui.theme.TypeElectric
import com.amartinez.pokeapp.ui.theme.TypeFairy
import com.amartinez.pokeapp.ui.theme.TypeFighting
import com.amartinez.pokeapp.ui.theme.TypeFire
import com.amartinez.pokeapp.ui.theme.TypeFlying
import com.amartinez.pokeapp.ui.theme.TypeGhost
import com.amartinez.pokeapp.ui.theme.TypeGrass
import com.amartinez.pokeapp.ui.theme.TypeGround
import com.amartinez.pokeapp.ui.theme.TypeIce
import com.amartinez.pokeapp.ui.theme.TypeNormal
import com.amartinez.pokeapp.ui.theme.TypePoison
import com.amartinez.pokeapp.ui.theme.TypePsychic
import com.amartinez.pokeapp.ui.theme.TypeRock
import com.amartinez.pokeapp.ui.theme.TypeSteel
import com.amartinez.pokeapp.ui.theme.TypeWater
import java.security.MessageDigest

fun Long.formatId(): String = "#${this.toString().padStart(3, '0')}"

fun Long.formatNumber(): String = this.toString().padStart(3, '0')

fun String.capitalizedFirstChar(): String =this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}

@Composable
fun String.toTypeColor(): Color {
    return when (this.lowercase()) {
        "grass" -> TypeGrass
        "fire" -> TypeFire
        "water" -> TypeWater
        "bug" -> TypeBug
        "electric" -> TypeElectric
        "ghost" -> TypeGhost
        "normal" -> TypeNormal
        "psychic" -> TypePsychic
        "flying" -> TypeFlying
        "fighting" -> TypeFighting
        "dark" -> TypeDark
        "ground" -> TypeGround
        "rock" -> TypeRock
        "steel" -> TypeSteel
        "ice" -> TypeIce
        "dragon" -> TypeDragon
        "fairy" -> TypeFairy
        "poison" -> TypePoison
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}

fun String.toSha256(): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}

fun Context.findActivity(): FragmentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) return context
        context = context.baseContext
    }
    return null
}