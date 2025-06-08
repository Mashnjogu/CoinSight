package com.project.coinsight.domain.model

data class SearchCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val thumb: String,
    val marketCapRank: Int?
)