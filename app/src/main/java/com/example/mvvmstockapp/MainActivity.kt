package com.example.mvvmstockapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmstockapp.core.navigation.Route
import com.example.mvvmstockapp.stock_features.presentation.company_listings.CompanyListingScreen
import com.example.mvvmstockapp.ui.theme.MVVMStockAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMStockAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Route.CompanyListingScreen
                        ){
                            composable(Route.CompanyListingScreen){
                                CompanyListingScreen(
                                    onNavigate = {
                                                 navController.navigate(it.route)
                                    },
                                    scaffoldState = scaffoldState
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

