package com.example.juegos_moviles.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegos_moviles.dto.ChoicesApiResponse
import com.example.juegos_moviles.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.Int

data class EedUiState(
    val playerPoints: Int = 0,
    val cpuPoints: Int = 0,
    val userChoice: String = "",
    val cpuChoice: String = "",
    val response: ChoicesApiResponse? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class EedViewModel: ViewModel() {
    companion object {
        private const val TAG = "UiEedState"
    }
    private val _uiEedState = MutableStateFlow(EedUiState())
    val uiEedState: StateFlow<EedUiState> = _uiEedState

    fun setUserChoice(userChoice: String) {
        _uiEedState.value = _uiEedState.value.copy(
            userChoice = userChoice,
            cpuChoice = "",
            response = null,
            loading = false,
            error = null
        )
    }

    fun fight() {
        val _userChoice = _uiEedState.value.userChoice

        Log.d(TAG, "User choice: $_userChoice")

        if(_userChoice == "") {
            _uiEedState.value = _uiEedState.value.copy(
                userChoice = "",
                cpuChoice = "",
                response = null,
                loading = false,
                error = "Selecciona una opción"
            )
            return
        }

        viewModelScope.launch {
            try {
                _uiEedState.value = _uiEedState.value.copy(
                    userChoice = "",
                    cpuChoice = "",
                    response = null,
                    loading = true,
                    error = null
                )

                val apiResponse = Network.eedApi.fight(_userChoice)
                if(apiResponse.isSuccessful) {
                    Log.d(TAG, "API response: ${apiResponse.body()}")
                    _uiEedState.value = _uiEedState.value.copy(
                        playerPoints = _uiEedState.value.playerPoints + (apiResponse.body()?.result
                            ?: 0),
                        cpuPoints = _uiEedState.value.cpuPoints + ((apiResponse.body()?.result?.times(-1))
                            ?: 0),
                        userChoice = "",
                        cpuChoice = (apiResponse.body()?.choices?.cpu
                            ?: ""),
                        response = apiResponse.body(),
                        loading = false,
                        error = null
                    )
                } else {
                    _uiEedState.value = _uiEedState.value.copy(
                        response = null,
                        loading = false,
                        error = "Error HTTP: ${apiResponse.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiEedState.value = _uiEedState.value.copy(
                    response = null,
                    loading = false,
                    error = "Error de conexión: ${e.message}"
                )
            }
        }
    }

}