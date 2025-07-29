package UI.PatientUI.Homepage

import Database.ViewModel
import UI.Common.TopBar
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkcontainer
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Homepage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.isHomePage = true
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isHomePage = false
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Spacer(Modifier.height(80.dp))

                FactsCard(viewModel)
                ChatAICard(viewModel, navController)

                // Footer
                Spacer(Modifier.height(90.dp))
            }
        }
    }
}

@Composable
private fun FactsCard(
    viewModel: ViewModel
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Darkcontainer else Lightcontainer,
            contentColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(8.dp, spotColor = Darkpink, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Text(
            text = "Skin infections occur when germs like bacteria, viruses, fungi, or parasites penetrate the skin and spread, often through breaks in the skin. Common symptoms include rashes, swelling, redness, and pus formation.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(8.dp)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 10,
            lineHeight = 25.sp,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun ChatAICard(
    viewModel: ViewModel,
    navController: NavController
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Darkcontainer else Lightcontainer,
            contentColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(12.dp, spotColor = Darkpink, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .animateContentSize(),
        onClick = {
            navController.navigate("chatpage")
            viewModel.isChatPage = false
        }

    ) {
        Text(
            text = "Chat with AI assistant",
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(8.dp)),
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            lineHeight = 1.sp,
            letterSpacing = 0.sp
        )
    }
}