package com.project.coinsight.data.model

data class CoinDetailDTO(
    val id: String,
    val name: String,
    val symbol: String,
    val market_cap_rank: Int,
    val image: CoinImageDTO,
    val description: CoinDescriptionDTO,
    val hashing_algorithm: String?,
    val homepage: List<String>?,
    val genesis_date: String?,
    val market_data: MarketDataDTO?
)

