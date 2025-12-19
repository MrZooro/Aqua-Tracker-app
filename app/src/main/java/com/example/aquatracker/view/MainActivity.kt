package com.example.aquatracker.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aquatracker.ui.theme.AquaTrackerTheme
import com.example.aquatracker.viewModel.MainActivityViewModel

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
                var showEditDialog by remember { mutableStateOf(false) }
                val mainActivityViewModel: MainActivityViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MainTabRow(
                            currentRoute ?: NavigationRoute.HotWaterRoute.route,
                            navController
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { showEditDialog = true },
                        ) {
                            Icon(Icons.Filled.Add, "Floating action button.")
                        }
                    }
                ) { contentPadding ->
                    NavGraph(navController, paddingValues = contentPadding)

                    if (showEditDialog) {
                        EditAquaDialog(
                            onDismiss = {
                                showEditDialog = false
                            },
                            onConfirm = { value, date ->
                                if (currentRoute == NavigationRoute.HotWaterRoute.route) {
                                    mainActivityViewModel.insertAquaItem(
                                        itemValue = value,
                                        itemDate = date,
                                        itemIsHot = true
                                    )
                                } else {
                                    mainActivityViewModel.insertAquaItem(
                                        itemValue = value,
                                        itemDate = date,
                                        itemIsHot = false
                                    )
                                }
                                showEditDialog = false
                            },
                            onCancel = {
                                showEditDialog = false
                            }
                        )
                    }
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainTabRow(currentRoute: String, navController: NavController) {
        val tabs = listOf(
            NavigationRoute.HotWaterRoute,
            NavigationRoute.ColdWaterRoute
        )
        val selectedDestination = tabs.indexOfFirst { it.route == currentRoute }
            .coerceAtLeast(0)

        PrimaryTabRow(
            modifier = Modifier.statusBarsPadding(),
            selectedTabIndex = selectedDestination
        ) {
            tabs.forEachIndexed { index, destination ->
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
    }
}