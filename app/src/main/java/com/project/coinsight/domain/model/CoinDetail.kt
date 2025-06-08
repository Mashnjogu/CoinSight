package com.project.coinsight.domain.model

data class CoinDetail(
    val id: String,
    val name: String,
    val symbol: String,
    val marketCapRank: Int,
    val imageUrl: String,
    val description: String?,
    val hashingAlgorithm: String?,
    val homepageUrl: String?,
    val genesisDate: String?,
    val currentPriceUSD: Double?,
    val marketCapUSD: Double?,
    val totalVolumeUSD: Double?
)
