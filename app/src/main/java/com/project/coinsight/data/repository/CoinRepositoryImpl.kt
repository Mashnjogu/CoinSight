package com.project.coinsight.data.repository

import android.util.Log
import com.project.coinsight.data.api.CoinAPIService
import com.project.coinsight.data.mapper.toChartData
import com.project.coinsight.data.mapper.toCoin
import com.project.coinsight.data.mapper.toCoinDetail
import com.project.coinsight.data.mapper.toSearchCoin
import com.project.coinsight.domain.model.ChartData
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.domain.model.CoinDetail
import com.project.coinsight.domain.model.SearchCoin
import com.project.coinsight.domain.repository.CoinRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinAPIService
): CoinRepository{
    override suspend fun getTopCoins(): Flow<List<Coin>> = flow{

        Log.d("CoinRepositoryImpl", "Calling API")
                val coins = apiService.getTopCoins().map { it ->
                    it.toCoin()
                }
        Log.d("CoinRepositoryImpl", "API returned ${coins.size} coins")
                emit(coins)


    }

    override suspend fun getCoinDetails(coinId: String): CoinDetail {
        return apiService.getCoinDetails(coinId).toCoinDetail()
    }

    override suspend fun searchCoin(query: String): List<SearchCoin> {
        return apiService.searchCoins(query).coins.map { it.toSearchCoin() }
    }

    override suspend fun getMarketChart(coinId: String, days: String): List<ChartData> {
        return apiService.getMarketChart(coinId, days = days).toChartData()
    }
}