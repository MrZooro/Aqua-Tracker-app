package com.example.aquatracker.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aquatracker.ui.theme.AquaTrackerTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AquaTrackerTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val tabs = listOf(
                    NavigationRoute.HotWaterRoute,
                    NavigationRoute.ColdWaterRoute
                )
                val selectedDestination = tabs.indexOfFirst { it.route == currentRoute }
                    .coerceAtLeast(0)

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    PrimaryTabRow(
                        modifier = Modifier.statusBarsPadding(),
                        selectedTabIndex = selectedDestination
                    ) {
                        NavigationRoute.entries.forEachIndexed { index, destination ->
                            Tab(
                                selected = selectedDestination == index,
                                onClick = {
                                    navController.navigate(route = destination.route)
                                },
                                text = {
                                    Text(
                                        text = getString(destination.label),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )
                        }
                    }
                }) { contentPadding ->
                    NavGraph(navController, paddingValues = contentPadding)
                }
            }
        }
    }

    /**
     * В зависимости от NavigationRoute открывает соответствующий экран
     *
     * @param [navController] navController
     * @param [paddingValues] Отступы, для экранов
     */
    @Composable
    private fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.HotWaterRoute.route
        ) {

            composable(NavigationRoute.HotWaterRoute.route) {
                HotWaterScreen(paddingValues)
            }

            composable(NavigationRoute.ColdWaterRoute.route) {
                ColdWaterScreen(paddingValues)
            }
        }
    }
}