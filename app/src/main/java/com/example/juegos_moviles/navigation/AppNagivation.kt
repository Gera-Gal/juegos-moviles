package com.example.juegos_moviles.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.juegos_moviles.navigation.screen.AdivinaNumeroScreen
import com.example.juegos_moviles.navigation.screen.EedScreen
import com.example.juegos_moviles.navigation.screen.LoteriaScreen
import com.example.juegos_moviles.navigation.screen.MainMenuScreen
import com.example.juegos_moviles.viewmodel.EedViewModel
import com.example.juegos_moviles.viewmodel.GuessViewModel
import com.example.juegos_moviles.viewmodel.LotteryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNagivation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val eedVM: EedViewModel = viewModel()
    val lotteryVM: LotteryViewModel = viewModel()
    val guessViewModel: GuessViewModel = viewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "mainMenu"

    fun go(route: String) {
        scope.launch { drawerState.close() }
        if (currentRoute != route) {
            /*
            navController.navigate(route) {
                // evita apilar pantallas repetidas al usar drawer
                launchSingleTop = true
                restoreState = true // esta opción permite recuperar la pantalla guardada
                popUpTo("main") { saveState = true }  // esta opción permite guardar pantallas navegadas y el estado que tenían (scroll, campos)
                // no me sirvió porque tengo doble método de navegación
            }
             */
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = false
                }
                launchSingleTop = true
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Text(
                    text = "Juegos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Pantalla de selección")},
                    selected = currentRoute == "mainMenu",
                    onClick = { go("mainMenu") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Espada, Escudo o Dragón")},
                    selected = currentRoute == "eed",
                    onClick = { go("eed") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Lotería")},
                    selected = currentRoute == "lottery",
                    onClick = { go("lottery") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = {Text("Adivina el número")},
                    selected = currentRoute == "guess",
                    onClick = { go("guess") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            when (currentRoute) {
                                "mainMenu" -> "Pantalla de selección"
                                "eed" -> "Espada, Escudo o Dragón"
                                "lottery" -> "Lotería"
                                "guess" -> "Adivina el número"
                                else -> "App"
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = "Menú"
                            )
                        }
                    }
                )
            }
        ) {
                paddingValues ->
            NavHost (
                navController = navController,
                startDestination = "mainMenu",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route ="mainMenu") {
                    MainMenuScreen(navController = navController)
                }
                composable("eed") {
                    EedScreen(vm = eedVM)
                }
                composable("lottery") {
                    LoteriaScreen(vm = lotteryVM)
                }
                composable("guess") {
                    AdivinaNumeroScreen(vm = guessViewModel)
                }
            }
        }
    }
}