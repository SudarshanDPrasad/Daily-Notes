package com.sudarshan.dailynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudarshan.dailynotes.presentation.CreateNotesScreen
import com.sudarshan.dailynotes.presentation.HomeScreen
import com.sudarshan.dailynotes.presentation.HomeViewModel
import com.sudarshan.dailynotes.ui.theme.DailyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DailyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(viewModel = viewModel) {
                                navController.navigate("CreateNotesScreen")
                            }
                        }
                        composable("CreateNotesScreen") {
                            CreateNotesScreen(
                                viewModel = viewModel,
                                onAddClick = {
                                    navController.navigate("HomeScreen") {
                                        popUpTo("CreateNotesScreen") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
