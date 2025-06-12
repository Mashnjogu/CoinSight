package com.project.coinsight.data.repository

import android.util.Log
import com.project.coinsight.data.api.CoinAPIService
import com.project.coinsight.data.local.dao.CoinDao
import com.project.coinsight.data.local.entity.CoinEntity
import com.project.coinsight.data.mapper.toChartData
import com.project.coinsight.data.mapper.toCoinDetail
import com.project.coinsight.data.mapper.toEntity
import com.project.coinsight.data.mapper.toSearchCoin
import com.project.coinsight.domain.model.ChartData
import com.project.coinsight.domain.model.CoinDetail
import com.project.coinsight.domain.model.SearchCoin
import com.project.coinsight.domain.repository.CoinRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinAPIService,
    private val coinDao: CoinDao
): CoinRepository{
    override suspend fun getTopCoins(): Flow<List<CoinEntity>>{

//        Log.d("CoinRepositoryImpl", "Calling API")
//                val coins = apiService.getTopCoins().map { it ->
//                    it.toCoin()
//                }
//        Log.d("CoinRepositoryImpl", "API returned ${coins.size} coins")
//                emit(coins)
        val coins = coinDao.getAllCoins()
        Log.d("CoinDebug", "💾 [Room] Emitting coins: $coins")
        return coinDao.getAllCoins()
    }

    override suspend fun refreshCoins() {
        val topCoins = apiService.getTopCoins()
        coinDao.clearAll()
        coinDao.insertAll(topCoins.map { it.toEntity() })
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