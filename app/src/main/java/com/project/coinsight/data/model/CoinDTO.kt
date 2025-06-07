package com.project.coinsight.data.model

import com.google.gson.annotations.SerializedName

data class CoinDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price")
    val currentPrice: Double?,
    @SerializedName("market_cap")
    val marketCap: Long?,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int?,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Long?,
    @SerializedName("total_volume")
    val totalVolume: Double?,
    @SerializedName("high_24h")
    val high24h: Double?,
    @SerializedName("low_24h")
    val low24h: Double?,
    @SerializedName("price_change_24h")
    val priceChange24h: Double?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7d: Double?,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
    @SerializedName("max_supply")
    val maxSupply: Double?,
    @SerializedName("last_updated")
    val lastUpdated: String
)
