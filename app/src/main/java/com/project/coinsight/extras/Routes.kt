package com.project.coinsight.extras

import kotlinx.serialization.Serializable

sealed class Routes(val route: String){
    object HomeScreen: Routes("Homescreen")
    object CoinDetail: Routes("CoinDetail/${Args.coinId}"){
        object Args{
            const val coinId = "coinId"
        }
    }
    object ShowDetail: Routes("ShowDetail/${Args.tvShowId}"){
        object Args{
            const val tvShowId = "tvShowId"
        }
    }
}

const val COIN_DETAIL_ID_KEY = "coinId"
const val TV_SHOWDETAIL_ID_KEY = "tvShowId"


@Serializable
object HomeScreenType

@Serializable
data class CoinDetailType(val coinId: String)