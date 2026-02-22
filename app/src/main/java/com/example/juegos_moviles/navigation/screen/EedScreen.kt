package com.example.juegos_moviles.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.juegos_moviles.component.ChoiceButton
import com.example.juegos_moviles.component.CustomButton
import com.example.juegos_moviles.viewmodel.EedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EedScreen(
    vm: EedViewModel
) {
    val state by vm.uiEedState.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Marcador\nJugador: ${state.playerPoints}\tCPU: ${state.cpuPoints}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Selecciona una opción y... ¡Espada, Escudo o Dragón!",
                style = MaterialTheme.typography.titleLarge
            )

            CustomButton(
                description = "¡Espada, Escudo o Dragón!",
                onClick = { vm.fight() }
            )

            state.response?.let { result ->
                Card() {
                    Column(
                    ) {
                        Text(
                            text = result.message,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            if(state.error != null) {
                Card() {
                    Column(
                    ) {
                        Text(
                            text = state.error!!,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            if(state.userChoice != "") {
                // TODO: Do sth
            }

            if(state.loading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                ChoiceButton(
                    emoji = "🗡️",
                    name = "ESPADA",
                    description = "MATA A DRAGÓN",
                    onClick = { vm.setUserChoice("SWORD") },
                    selected = state.userChoice == "SWORD"
                )
                ChoiceButton(
                    emoji = "🛡️️",
                    name = "ESCUDO",
                    description = "DESTRUYE ESPADA",
                    onClick = { vm.setUserChoice("SHIELD") },
                    selected = state.userChoice == "SHIELD"
                )
                ChoiceButton(
                    emoji = "🐉️",
                    name = "DRAGÓN",
                    description = "DESTRUYE ESCUDO",
                    onClick = { vm.setUserChoice("DRAGON") },
                    selected = state.userChoice == "DRAGON"
                )
            }
        }
    }
}