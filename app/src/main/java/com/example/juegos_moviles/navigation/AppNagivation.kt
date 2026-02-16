package com.example.juegos_moviles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juegos_moviles.navigation.screen.AdivinaNumeroScreen
import com.example.juegos_moviles.navigation.screen.EEDScreen
import com.example.juegos_moviles.navigation.screen.LoteriaScreen
import com.example.juegos_moviles.navigation.screen.MainMenuScreen
import com.example.juegos_moviles.viewmodel.EEDViewModel
import com.example.juegos_moviles.viewmodel.GuessViewModel
import com.example.juegos_moviles.viewmodel.LotteryViewModel

@Composable
fun AppNagivation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val eedVM: EEDViewModel = viewModel()
    val lotteryVM: LotteryViewModel = viewModel()
    val guessViewModel: GuessViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "mainMenu"
    ) {
        composable("mainMenu") {
            MainMenuScreen(navController = navController)
        }
        composable("eed") {
            EEDScreen(navController = navController, vm = eedVM)
        }
        composable("loteria") {
            LoteriaScreen(navController = navController, vm = lotteryVM)
        }
        composable("adivina") {
            AdivinaNumeroScreen(navController = navController, vm = guessViewModel)
        }
    }
}