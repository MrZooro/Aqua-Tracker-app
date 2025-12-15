package com.example.aquatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aquatracker.ui.theme.AquaTrackerTheme
import com.example.aquatracker.view.NavigationRoute
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    private var navController: NavHostController? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AquaTrackerTheme {
                navController = rememberNavController()
                val startDestination = NavigationRoute.HotWaterRoute
                var selectedDestination by rememberSaveable {
                    mutableIntStateOf(startDestination.ordinal)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
                    PrimaryTabRow(
                        selectedTabIndex = selectedDestination,
                        modifier = Modifier.padding(contentPadding)
                    ) {
                        NavigationRoute.entries.forEachIndexed { index, destination ->
                            Tab(
                                selected = selectedDestination == index,
                                onClick = {
                                    navController?.navigate(route = destination.route)
                                    selectedDestination = index
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
                    NavGraph(paddingValues = contentPadding)
                }
            }
        }
    }

    @Composable
    private fun NavGraph(paddingValues: PaddingValues) {
        navController?.let { tempNavController ->

            NavHost(
                navController = tempNavController,
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

    @Composable
    fun ColdWaterScreen(x0: PaddingValues) {
        Text(text = "ColdWaterScreen")
    }

    @Composable
    fun HotWaterScreen(x0: PaddingValues) {
        Text(text = "HotWaterScreen")
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AquaTrackerTheme {
            Greeting("Android")
        }
    }
}