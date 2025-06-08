package com.project.coinsight.presentation.ui.CoinDetail

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
class CoinDetailViewModel @Inject constructor(
    private val repository: CoinRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CoinDetailUiState())
    val uiState: StateFlow<CoinDetailUiState> = _uiState.asStateFlow()

    fun loadCoinDetails(coinId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val coin = repository.getCoinDetails(coinId)
//                val chartData = repository.getMarketChart(coinId, "7")

                _uiState.value = _uiState.value.copy(
                    coin = coin,
//                    chartData = chartData,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }


//    fun updateTimeRange(days: String) {
//        val coinId = _uiState.value.coin?.id ?: return
//        viewModelScope.launch {
//            try {
//                val chartData = repository.getMarketChart(coinId, days)
//                _uiState.value = _uiState.value.copy(
//                    chartData = chartData,
//                    selectedTimeRange = days
//                )
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }


}