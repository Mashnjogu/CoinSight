package com.project.coinsight.data.mapper

import com.project.coinsight.data.model.CoinDTO
import com.project.coinsight.domain.model.Coin

fun CoinDTO.toCoin(): Coin{
    return Coin(
        id = id,
        symbol = symbol.uppercase(),
        name = name,
        image = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap ?: 0L,
        marketCapRank = marketCapRank ?: 0,
        totalVolume = totalVolume ?: 0L,
        high24h = high24h ?: 0.0,
        low24h = low24h ?: 0.0,
        priceChange24h = priceChange24h ?: 0.0,
        priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
        priceChangePercentage7d = priceChangePercentage7d ?: 0.0,
        circulatingSupply = circulatingSupply ?: 0.0,
        totalSupply = totalSupply,
        maxSupply = maxSupply,
        lastUpdated = lastUpdated
    )
}