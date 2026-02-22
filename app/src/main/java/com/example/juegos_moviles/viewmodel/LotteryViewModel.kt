package com.example.juegos_moviles.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegos_moviles.dto.NumberApiResponse
import com.example.juegos_moviles.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LotteryUiState(
    val playerPoints: Int = 0,
    val winningNumber: String = "",
    val userNumber: String = "",
    val digits: String = "1",
    val response: NumberApiResponse? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class LotteryViewModel: ViewModel() {
    companion object {
        private const val TAG = "LoteriaViewModel"
    }

    private val _uiLotteryState = MutableStateFlow(LotteryUiState())
    val lotteryState: StateFlow<LotteryUiState> = _uiLotteryState

    fun setUserNumber(userNumber: String) {
        _uiLotteryState.value = _uiLotteryState.value.copy(
            userNumber = userNumber,
            response = null,
            loading = false,
            error = null
        )
    }

    fun setDigits(digits: String) {
        _uiLotteryState.value = _uiLotteryState.value.copy(
            digits = digits,
            response = null,
            loading = false,
            error = null
        )
    }

    fun playLottery() {
        val _userNumber = _uiLotteryState.value.userNumber
        val _digits = _uiLotteryState.value.digits

        Log.d(TAG, "User number: $_userNumber")
        Log.d(TAG, "Digits: $_digits")

        if (_userNumber == "") {
            _uiLotteryState.value = _uiLotteryState.value.copy(
                winningNumber = "",
                response = null,
                loading = false,
                error = "Ingresa el número de dígitos y tu número de la suerte"
            )
            return
        }

        viewModelScope.launch {
            try {
                _uiLotteryState.value = _uiLotteryState.value.copy(
                    winningNumber = "",
                    digits = "1",
                    response = null,
                    loading = true,
                    error = null
                )

                val apiResponse = Network.lotteryApi.playLottery(_userNumber, _digits.toInt())
                if (apiResponse.isSuccessful) {
                    Log.d(TAG, "API response: ${apiResponse.body()}")
                    var playerPoints: Int = 0
                    if(apiResponse.body()?.number == _userNumber.toInt()) {
                        playerPoints = _uiLotteryState.value.playerPoints + 1
                    }  else {
                        playerPoints = _uiLotteryState.value.playerPoints
                    }
                    _uiLotteryState.value = _uiLotteryState.value.copy(
                        playerPoints = playerPoints,
                        winningNumber = apiResponse.body()?.number.toString(),
                        response = apiResponse.body(),
                        loading = false,
                        error = null
                    )
                } else {
                    _uiLotteryState.value = _uiLotteryState.value.copy(
                        response = null,
                        loading = false,
                        error = "Error HTTP: ${apiResponse.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiLotteryState.value = _uiLotteryState.value.copy(
                    response = null,
                    loading = false,
                    error = "Error de conexión: ${e.message}"
                )
            }
        }
    }
}