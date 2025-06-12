package com.project.coinsight.domain.repository

import com.project.coinsight.data.local.entity.CoinEntity
import com.project.coinsight.domain.model.ChartData
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.domain.model.CoinDetail
import com.project.coinsight.domain.model.SearchCoin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getTopCoins(): Flow<List<CoinEntity>>
    suspend fun refreshCoins()
    suspend fun getCoinDetails(coinId: String): CoinDetail
    suspend fun searchCoin(query: String): List<SearchCoin>
    suspend fun getMarketChart(coinId: String, days: String): List<ChartData>
}