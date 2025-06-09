package com.project.coinsight.data.model

import com.google.gson.annotations.SerializedName

data class MarketChartDto(
    val prices: List<List<Double>>,
    @SerializedName("market_caps")
    val marketCaps: List<List<Double>>,
    @SerializedName("total_volumes")
    val totalVolumes: List<List<Double>>
)