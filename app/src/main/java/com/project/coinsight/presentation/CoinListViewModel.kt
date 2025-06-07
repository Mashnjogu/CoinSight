package com.project.coinsight.presentation

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
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.getTopCoins().collect { coins ->
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

    fun refreshCoins() {
        loadCoins()
    }
}