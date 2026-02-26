package com.amartinez.pokeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.amartinez.pokeapp.presentation.navigation.NavigationWrapper
import com.amartinez.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokeAppTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavigationWrapper()
                    }
                }
            }
        }
    }
}