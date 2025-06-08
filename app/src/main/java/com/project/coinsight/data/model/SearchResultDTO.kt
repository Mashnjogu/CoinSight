package com.project.coinsight.data.model

import com.google.gson.annotations.SerializedName

data class SearchResultDTO(
    val coins: List<SearchCoinDTO>
)

data class SearchCoinDTO(
    val id: String,
    val name: String,
    val symbol: String,
    val thumb: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int?
)
