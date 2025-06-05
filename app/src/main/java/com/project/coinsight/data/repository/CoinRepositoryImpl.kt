package com.project.coinsight.data.repository

import com.project.coinsight.data.api.CoinAPIService
import com.project.coinsight.data.mapper.toCoin
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.domain.repository.CoinRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinAPIService
): CoinRepository{
    override suspend fun getTopCoins(): Flow<List<Coin>> = flow{
        while (true){
            try {
                val coins = apiService.getTopCoins().map { it ->
                    it.toCoin()
                }
                emit(coins)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override suspend fun getCoinDetails(coinId: String): Coin {
        TODO("Not yet implemented")
    }
}