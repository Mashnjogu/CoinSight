package com.project.coinsight.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.coinsight.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CoinListUiState())
    val uiState: StateFlow<CoinListUiState> = _uiState.asStateFlow()

    init {
        loadCoins()
        testApi()
    }


    private fun loadCoins() {

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.getTopCoins().collect { coins ->
                    val coins = coins
                    Log.d("CoinsViewModel", "The coins list is of size ${coins.size}")
                    _uiState.value = _uiState.value.copy(
                        coins = coins,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun testApi() {
        viewModelScope.launch {
            try {
                Log.d("CoinDebug", "Starting API test...")
                repository.getTopCoins().collect { coins ->
                    Log.d("CoinDebug", "✅ API returned ${coins.size} coins")
                    coins.forEachIndexed { index, coin ->
                        Log.d("CoinDebug", "[$index] ${coin.name} (${coin.symbol}) - ${coin.currentPrice}")
                    }
                }
            } catch (e: Exception) {
                Log.e("CoinDebug", "❌ Error while fetching coins: ${e.message}", e)
            }
        }
    }

    fun refreshCoins() {
        loadCoins()
    }
}