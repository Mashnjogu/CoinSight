package com.project.coinsight.extras

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

const val MOVIE_DETAIL_ID_KEY = "movieId"
const val TV_SHOWDETAIL_ID_KEY = "tvShowId"