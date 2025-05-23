package com.example.aiskincure

import Database.ViewModel
import Navigation.BottomNavigationBar
import Navigation.NavGraph
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aiskincure.ui.theme.AISkinCureTheme
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG

class MainActivity : ComponentActivity() {
    lateinit var viewModel : ViewModel
    lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if(!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        } else {
            viewModel.permissionGranted = true
            Toast.makeText(applicationContext,"Permission Granted", Toast.LENGTH_SHORT).show()
        }

        setContent {
            AISkinCureTheme {
                navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                Scaffold(
                    bottomBar = {
                        if(viewModel.isChatPage) {
                            BottomNavigationBar(
                                viewModel,
                                navController,
                                navBackStackEntry
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
                        NavGraph(
                            navController,
                            viewModel
                        )
                    }
                }

            }
        }
    }

    fun hasRequiredPermissions() : Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}