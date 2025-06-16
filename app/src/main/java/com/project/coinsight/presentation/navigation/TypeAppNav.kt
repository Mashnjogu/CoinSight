package com.project.coinsight.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.project.coinsight.extras.CoinDetailType
import com.project.coinsight.extras.HomeScreenType
import com.project.coinsight.extras.Routes
import com.project.coinsight.presentation.ui.CoinDetail.CoinDetailScreen
import com.project.coinsight.presentation.ui.CoinList.CoinListScreen

@Composable
fun  TypeAppNav(paddingValues: PaddingValues){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenType
    ){
        composable<HomeScreenType> {
            CoinListScreen(
                paddingValues = paddingValues,
                onNavigateToCoinDetails = { coinId ->
                    navController.navigate(CoinDetailType(coinId = coinId))
                }
            )
        }

        composable<CoinDetailType> {
            val args = it.toRoute<CoinDetailType>()
            CoinDetailScreen(
                coinId = args.coinId,
            )

        }
    }
}