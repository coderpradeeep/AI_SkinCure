package UI.PatientUI.Historypage

import Database.ViewModel
import UI.Common.TopBar
import UI.PatientUI.ConsultantPage.ConsultansCard
import UI.PatientUI.ConsultantPage.consultants
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Historypage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.isHistoryPage = true
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isHistoryPage = false
        }
    }

    Scaffold(
        topBar = {
            TopBar( viewModel, navController)
        }
    ) {
        Surface(
            color = if (isSystemInDarkTheme()) DarkBG else BG,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Column(
                //state = scrollState,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Spacer(Modifier.height(60.dp))



                // Footer
                Spacer(Modifier.height(80.dp))
            }

        }
    }
}