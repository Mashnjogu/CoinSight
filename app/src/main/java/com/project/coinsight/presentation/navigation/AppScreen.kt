package com.project.coinsight.presentation.navigation

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.coinsight.extras.COIN_DETAIL_ID_KEY
import com.project.coinsight.extras.CoinAppState
import com.project.coinsight.extras.Routes
import com.project.coinsight.presentation.ui.CoinDetail.CoinDetailScreen
import com.project.coinsight.presentation.ui.CoinList.CoinListScreen
import com.project.coinsight.presentation.utils.snackbar.SnackBarManager
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppScreen(paddingValues: PaddingValues){

    val appState = rememberAppState()
    val navController = rememberNavController()
    Scaffold(

        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(snackbarData = snackbarData, contentColor = MaterialTheme.colorScheme.primary)

                }
            )
        }
        ) { innerPaddingModifier ->
        NavHost(
            navController = appState.navController,
            startDestination = Routes.HomeScreen.route,
            modifier = Modifier.padding(innerPaddingModifier)
        ){
            CoinSightGraph(
                appState = appState,
                navController = appState.navController,
                padding = paddingValues
            )
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember{ SnackbarHostState()},
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope){
    CoinAppState(snackbarHostState,navController,snackbarManager,resources,coroutineScope)
}

@Composable
fun resources(): Resources{
    return LocalContext.current.resources
}

fun NavGraphBuilder.CoinSightGraph(appState: CoinAppState, navController: NavHostController, padding: PaddingValues){
    composable(Routes.HomeScreen.route){
        CoinListScreen(
            paddingValues = padding,
            onNavigateToCoinDetails = {coinId ->
                navController.navigate("${Routes.CoinDetail.route}/$coinId")
            }
        )
    }

    composable(
        "${Routes.CoinDetail.route}/{${Routes.CoinDetail.Args.coinId}}",
        arguments = listOf(
            navArgument(COIN_DETAIL_ID_KEY){type = NavType.StringType}
        )
    ){ backStackEntry ->
        val coinId = backStackEntry.arguments?.getString(COIN_DETAIL_ID_KEY)

        if (coinId != null){
            CoinDetailScreen(
                coinId = coinId,
                onNavigateToCoinDetails = { coinId ->
                navController.navigate("${Routes.ShowDetail.route}/$coinId")
            })
        }

    }
}