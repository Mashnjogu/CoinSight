package com.project.coinsight.data.api

import com.project.coinsight.data.model.CoinDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPIService {

    @GET("coins/markets")
    suspend fun getTopCoins(
        @Query("vs_currency") currency: String = "btc",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String = "1h,24h,7d"
    ): List<CoinDTO>
}