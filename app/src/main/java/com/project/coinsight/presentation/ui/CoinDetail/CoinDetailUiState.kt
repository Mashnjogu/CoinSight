package com.project.coinsight.presentation.ui.CoinDetail


import com.project.coinsight.domain.model.ChartData
import com.project.coinsight.domain.model.CoinDetail

data class CoinDetailUiState(
    val coin: CoinDetail? = null,
    val chartData: List<ChartData> = emptyList(),
    val selectedTimeRange: String = "7",
    val isLoading: Boolean = false,
    val error: String? = null
)
