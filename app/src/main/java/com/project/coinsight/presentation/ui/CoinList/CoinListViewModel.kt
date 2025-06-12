package com.project.coinsight.presentation.ui.CoinList

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.coinsight.data.mapper.toCoin
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


//    public fun loadCoins() {
//
//        //collect from the database
//        viewModelScope.launch {
//            repository.getTopCoins().collect{entities ->
//                Log.d("CoinDebug", "💾 [Room] Emitting ${entities.size} cached coins")
//                _uiState.value = _uiState.value.copy(
//                    coins     = entities.map { it.toCoin() },
//                    isLoading = false,
//                    error     = null
//                )
//            }
//        }
//
//        //refresh in background
//        viewModelScope.launch {
////            _uiState.value = _uiState.value.copy(isLoading = true)
//            try {
//                Log.d("CoinDebug", "🌐 [API] Fetching fresh coins…")
//                repository.refreshCoins()
//                Log.d("CoinDebug", "💽 [Room] Inserted fresh coins into DB")
//                repository.getTopCoins().collect { coinEntity ->
//                    Log.d("CoinDebug", "✅ Fetched ${coinEntity.size} coins from Room DB")
//                    coinEntity.forEachIndexed { index, coin ->
//                        Log.d("CoinDebug", "[$index] ${coin.name} (${coin.symbol}) - ${coin.currentPrice}")
//                    }
//                    val coins = coinEntity.map { it.toCoin() }
//                    _uiState.value = _uiState.value.copy(
//                        coins = coins,
//                        isLoading = false,
//                        error = null
//                    )
//                }
//            } catch (e: Exception) {
//                _uiState.value = _uiState.value.copy(
//                    isLoading = false,
//                    error = e.message
//                )
//            }
//        }
//    }

    fun loadCoins() {
        //  collect DB flow — emits current cache and keeps UI updated
        viewModelScope.launch {
            repository.getTopCoins().collect { entities ->
                _uiState.value = _uiState.value.copy(
                    coins = entities.map { it.toCoin() },
                    isLoading = false,
                    error = null
                )
            }
        }

        //Background refresh — insert into DB if online
        viewModelScope.launch {
            try {
                Log.d("CoinDebug", "🌐 [API] Fetching fresh coins…")
                repository.refreshCoins()
                Log.d("CoinDebug", "💽 [Room] DB updated after API refresh")
                // No need to collect again — first coroutine above will emit updated DB automatically
            } catch (e: Exception) {
                Log.w("CoinDebug", "⚠️ Refresh failed: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Offline or failed to refresh. Showing cached data."
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