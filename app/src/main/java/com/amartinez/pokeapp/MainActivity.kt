package com.amartinez.pokeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.amartinez.pokeapp.presentation.navigation.NavigationWrapper
import com.amartinez.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokeAppTheme {
                val navController = rememberNavController()
                NavigationWrapper(navController = navController)
            }
        }
    }
}