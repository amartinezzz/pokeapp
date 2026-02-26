package com.amartinez.pokeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.amartinez.pokeapp.presentation.screens.detail.DetailScreen
import com.amartinez.pokeapp.presentation.screens.home.HomeScreen
import com.amartinez.pokeapp.presentation.screens.login.LogInScreen
import com.amartinez.pokeapp.presentation.screens.register.RegisterScreen

/**
 * Grafo de navegación principal de la aplicación.
 * * Se encarga de gestionar la transición entre pantallas, la definición de rutas
 * y la extracción de argumentos.
 *
 * @param navController El controlador de navegación que gestiona la pila de retroceso.
 */
@Composable
fun NavigationWrapper(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = LogIn
    ) {
        composable<LogIn> {
            LogInScreen(
                navToHome = {
                    navController.navigate(Home) {
                        popUpTo(LogIn) {
                            inclusive = true
                        }
                    }
                },
                navToRegister = {
                    navController.navigate(Register)
                }
            )
        }

        composable<Register> {
            RegisterScreen {
                navController.navigate(Home) {
                    popUpTo(LogIn) {
                        inclusive = true
                    }
                }
            }
        }

        composable<Home> {
            HomeScreen { id ->
                navController.navigate(Detail(id))
            }
        }

        composable<Detail> { navBackStackEntry ->
            val params = navBackStackEntry.toRoute<Detail>()
            DetailScreen(
                id = params.id ?: 0,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}