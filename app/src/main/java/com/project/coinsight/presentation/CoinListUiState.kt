package com.project.coinsight.presentation

import com.project.coinsight.domain.model.Coin

data class CoinListUiState(
    val coins: List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)