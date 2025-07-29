package UI.Common

import Database.ViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.delay

@Composable
fun SplashScreenWithLoading(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val isReady by viewModel.isAppReady.collectAsState()
    val userProfile by viewModel.userProfileInfo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.preloadAppData()
        Log.d("SplashScreenLoad", "App data preloaded $userProfile")
    }

    LaunchedEffect(isReady) {
        if (isReady) {
            delay(300) // UI transition delay

            if (userProfile != null) {
                // User is logged in, go to Home
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                // Give the app a bit more time in case userProfile is still loading
                delay(1000)
                navController.navigate("loginpage") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Darkpink, Color.Black),
                    startY = 0f,
                    endY = 2500f
                )
            ),
        color = Color.Transparent,
        ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Pink,
                strokeWidth = 4.dp,
                trackColor = Color.Transparent,
                modifier = Modifier
                    .scale(0.7f)
            )
        }
    }
}