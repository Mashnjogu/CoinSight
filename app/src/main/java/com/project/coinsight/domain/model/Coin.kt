package com.project.coinsight.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int,
    val totalVolume: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val circulatingSupply: Double,
    val totalSupply: Double?,
    val maxSupply: Double?,
    val lastUpdated: String?
) : Parcelable
