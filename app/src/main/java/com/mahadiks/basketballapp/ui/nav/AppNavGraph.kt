package com.mahadiks.basketballapp.ui.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mahadiks.basketballapp.ui.screens.ScheduleScreen
import com.mahadiks.basketballapp.viewmodel.ScheduleViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: ScheduleViewModel
) {
    NavHost(navController = navController, startDestination = "schedule") {
        composable("schedule") {
            ScheduleScreen(modifier,viewModel = viewModel)
        }
    }
}