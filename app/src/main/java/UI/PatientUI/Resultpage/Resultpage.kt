package UI.PatientUI.Resultpage

import Database.ViewModel
import UI.Common.TopBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Orange
import kotlinx.coroutines.launch

val result = listOf(
    "nv", "mel", "bkl", "bcc", "akiec", "vasc", "df"
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Resultpage(
    viewModel: ViewModel,
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()
    val loading by remember {
        derivedStateOf {
            viewModel.isLoading
        }
    }
    val bitmap = remember {
        derivedStateOf {
            viewModel.bitmap.value
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.isScanpage = true
        }
        bitmap.value?.let {
            viewModel.analyzeImage(it)
        }
        Log.d("LoadingDebug", "Loading changed to $loading at ${System.currentTimeMillis()}")
        //viewModel.random = result.random()
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isScanpage = false
            viewModel.cancelAnalysis()
        }
    }

    Scaffold(
        topBar = {
            TopBar(viewModel, navController)
        }
    ) {
        Surface(
            color = if (isSystemInDarkTheme()) DarkBG else BG,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Column {

                // Footer
                Spacer(Modifier.height(100.dp))

                Card(
                    modifier = Modifier
                        .padding(PaddingValues(16.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = viewModel.response.value ?: "No response yet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }

                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.enteredText = "this is a type of skin cancer '${viewModel.response}' : give information, cure, prevention and precautions. In brief"

                            navController.navigate("chatpage")
                            viewModel.isChatPage = false

                            // viewModel.addChat(viewModel.enteredText)
                            viewModel.isUser = false
                            viewModel.prompt = viewModel.enteredText
                            viewModel.chatAI(viewModel.prompt)
                            viewModel.enteredText = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(horizontal = 16.dp))
                ) {
                    Text(
                        text = "View more",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500
                    )
                }

                // Footer
                Spacer(Modifier.height(50.dp))
            }

            if(loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(if(isSystemInDarkTheme()) Color.Black.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.5f))
                ) {
                    CircularProgressIndicator(
                        color = Orange,
                        strokeWidth = 5.dp,
                    )
                }
            }

        }
    }
}