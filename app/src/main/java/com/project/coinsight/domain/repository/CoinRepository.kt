package com.project.coinsight.domain.repository

import com.project.coinsight.domain.model.Coin
import com.project.coinsight.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getTopCoins(): Flow<List<Coin>>
    suspend fun getCoinDetails(coinId: String): CoinDetail
}