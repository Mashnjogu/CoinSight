package com.project.coinsight.extras

import android.content.res.Resources
import androidx.navigation.NavHostController
import com.project.coinsight.presentation.utils.snackbar.SnackBarManager
import com.project.coinsight.presentation.utils.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState

class CoinAppState(
//    val scaffoldState: ScaffoldState,
    val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val snackbarManager: SnackBarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init{
        coroutineScope.launch {
            snackbarManager.snackBarMessage.filterNotNull().collect{ snackBarMessage ->
                val text = snackBarMessage.toMessage(resources)
                snackbarHostState.showSnackbar(text)
            }
        }
    }

    fun popUp(){
        navController.popBackStack()
    }

    fun navigate(route: String){
        navController.navigate(route){
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUpRoute:String){
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(popUpRoute){
                inclusive = true
            }
        }
    }

    fun clearAndNavigate(route: String){
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(0){
                inclusive = true
            }
        }
    }
}

