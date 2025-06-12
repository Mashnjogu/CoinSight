package com.project.coinsight.data.mapper

import com.project.coinsight.data.local.entity.CoinEntity
import com.project.coinsight.data.model.CoinDTO
import com.project.coinsight.data.model.CoinDetailDTO
import com.project.coinsight.data.model.MarketChartDto
import com.project.coinsight.data.model.SearchCoinDTO
import com.project.coinsight.domain.model.ChartData
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.domain.model.CoinDetail
import com.project.coinsight.domain.model.SearchCoin

fun CoinDTO.toCoin(): Coin{
    return Coin(
        id = id,
        symbol = symbol.uppercase(),
        name = name,
        image = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap ?: 0L,
        marketCapRank = marketCapRank ?: 0,
        totalVolume = totalVolume ?: 0.0,
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

fun CoinDTO.toEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice,
        priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
        marketCapRank = marketCapRank
    )
}

fun CoinEntity.toCoin(): Coin{
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = 0L,
        marketCapRank = marketCapRank ?: 0,
        totalVolume = 0.0,
        high24h = 0.0,
        low24h = 0.0,
        priceChange24h = 0.0,
        priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
        priceChangePercentage7d = 0.0,
        circulatingSupply = 0.0,
        totalSupply = null,
        maxSupply = null,
        lastUpdated = null
    )
}


fun CoinDetailDTO.toCoinDetail(): CoinDetail {
    return CoinDetail(
        id = id,
        name = name,
        symbol = symbol,
        marketCapRank = market_cap_rank,
        imageUrl = image.large,
        description = description.en,
        hashingAlgorithm = hashing_algorithm,
        homepageUrl = homepage?.firstOrNull { it.isNotBlank() },
        genesisDate = genesis_date,
        currentPriceUSD = market_data?.current_price?.get("usd"),
        marketCapUSD = market_data?.market_cap?.get("usd"),
        totalVolumeUSD = market_data?.total_volume?.get("usd")
    )
}


fun SearchCoinDTO.toSearchCoin(): SearchCoin {
    return SearchCoin(
        id = id,
        name = name,
        symbol = symbol.uppercase(),
        thumb = thumb,
        marketCapRank = marketCapRank
    )
}

fun MarketChartDto.toChartData(): List<ChartData> {
    return prices.map { priceArray ->
        ChartData(
            timestamp = priceArray[0].toLong(),
            price = priceArray[1]
        )
    }
}

