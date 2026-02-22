package com.example.juegos_moviles.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.juegos_moviles.R
import com.example.juegos_moviles.component.CustomButton
import com.example.juegos_moviles.viewmodel.LotteryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoteriaScreen(
    vm: LotteryViewModel
) {
    val state by vm.lotteryState.collectAsState()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.ticket)
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Marcador\nJugador: ${state.playerPoints}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Ingresa número de dígitos, tu número de la suerte y... ¡Jugar!",
                style = MaterialTheme.typography.titleLarge
            )

            CustomButton(
                description = "¡Jugar!",
                onClick = { vm.playLottery() }
            )

            state.response?.let { result ->
                Card() {
                    Column(
                    ) {
                        Text(
                            text = "El número ganador fue ${state.response!!.number}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
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

            if(state.userNumber != "") {
                // TODO: Do sth
            }

            if(state.digits != "") {
                // TODO: Do sth
            }

            if(state.loading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            }

            Text(
                text = "Número de dígitos",
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                value = state.digits,
                onValueChange = vm::setDigits,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            if(state.digits.toInt() > 1) {
                val digits = state.digits.toInt() - 1
                val validFormat = "9".padStart(digits, '0')
                Text(
                    text = "Cada dígito puede estar entre 0 y 1, \"${validFormat}\" sería válido",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = "Número de la suerte",
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                value = state.userNumber,
                onValueChange = vm::setUserNumber,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(80.dp)
                    .offset(x=40.dp,y=32.dp)
            )
        }
    }
}