package com.example.aiskincure

import Database.ViewModel
import Navigation.BottomNavigationBar
import Navigation.RootNavHost
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aiskincure.ui.theme.AISkinCureTheme
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG

class MainActivity : ComponentActivity() {
    lateinit var viewModel : ViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AISkinCureTheme {
                val rootNavController = rememberNavController()
                val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

                Scaffold(
                    bottomBar = {
                        if(viewModel.isChatPage) {
                            BottomNavigationBar(
                                viewModel,
                                rootNavController = rootNavController,
                                navBackStackEntry = navBackStackEntry
                            )
                        }
                    },

                ) {
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = if (isSystemInDarkTheme()) DarkBG else BG
                            )
                    ) {
                        RootNavHost(
                            rootNavController = rootNavController,
                            startDestination = "home",
                            viewModel
                        )
                    }
                }

            }
        }
    }
}