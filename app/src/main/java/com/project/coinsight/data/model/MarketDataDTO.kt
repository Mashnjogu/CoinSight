package com.project.coinsight.data.model

data class MarketDataDTO(
    val current_price: Map<String, Double>,
    val market_cap: Map<String, Double>,
    val total_volume: Map<String, Double>
)
