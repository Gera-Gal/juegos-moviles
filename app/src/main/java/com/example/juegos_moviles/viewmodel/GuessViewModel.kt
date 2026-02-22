package com.example.juegos_moviles.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegos_moviles.dto.NumberApiResponse
import com.example.juegos_moviles.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class GuessUiState(
    val playerPoints: Int = 0,
    val winningNumber: String = "",
    val userNumber: String = "",
    val maxNumber: String = "1",
    val response: NumberApiResponse? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class GuessViewModel: ViewModel() {
    companion object {
        private const val TAG = "GuessViewModel"
    }

    private val _uiGuessState = MutableStateFlow(GuessUiState())
    val guessState: StateFlow<GuessUiState> = _uiGuessState

    fun setUserNumber(userNumber: String) {
        _uiGuessState.value = _uiGuessState.value.copy(
            userNumber = userNumber,
            response = null,
            loading = false,
            error = null
        )
    }

    fun setMaxNumber(maxNumber: String) {
        _uiGuessState.value = _uiGuessState.value.copy(
            maxNumber = maxNumber,
            response = null,
            loading = false,
            error = null
        )
    }

    fun playLottery() {
        val _userNumber = _uiGuessState.value.userNumber
        val _maxNumber = _uiGuessState.value.maxNumber

        Log.d(TAG, "User number: $_userNumber")
        Log.d(TAG, "Digits: $_maxNumber")

        if (_userNumber == "") {
            _uiGuessState.value = _uiGuessState.value.copy(
                winningNumber = "",
                response = null,
                loading = false,
                error = "Ingresa el número máximo y tu número de la suerte"
            )
            return
        }

        viewModelScope.launch {
            try {
                _uiGuessState.value = _uiGuessState.value.copy(
                    winningNumber = "",
                    response = null,
                    loading = true,
                    error = null
                )

                val apiResponse = Network.guessApi.guessNumber(_userNumber.toInt(), _maxNumber.toInt())
                if (apiResponse.isSuccessful) {
                    Log.d(TAG, "API response: ${apiResponse.body()}")
                    var playerPoints: Int = 0
                    if(apiResponse.body()?.number == _userNumber.toInt()) {
                        playerPoints = _uiGuessState.value.playerPoints + 1
                    }  else {
                        playerPoints = _uiGuessState.value.playerPoints
                    }
                    _uiGuessState.value = _uiGuessState.value.copy(
                        playerPoints = playerPoints,
                        winningNumber = apiResponse.body()?.number.toString(),
                        response = apiResponse.body(),
                        loading = false,
                        error = null
                    )
                } else {
                    _uiGuessState.value = _uiGuessState.value.copy(
                        response = null,
                        loading = false,
                        error = "Error HTTP: ${apiResponse.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiGuessState.value = _uiGuessState.value.copy(
                    response = null,
                    loading = false,
                    error = "Error de conexión: ${e.message}"
                )
            }
        }
    }
}