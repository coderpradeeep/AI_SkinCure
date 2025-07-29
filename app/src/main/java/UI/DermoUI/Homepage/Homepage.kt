package UI.DermoUI.Homepage

import Database.DataType.PatientQueryDatatype
import Database.ViewModel
import UI.Common.TopBar
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkcontainer
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Lightcontainer


val sampleQueries = listOf(
                        PatientQueryDatatype("P001", "Neha Sharma", "2025-07-26 12:15", "Red spots on cheeks, slightly itchy.", null),
                        PatientQueryDatatype("P002", "Rahul Jain", "2025-07-26 11:30", "Acne breakout after sunscreen use.", null),
                        PatientQueryDatatype("P003", "Anita Rao", "2025-07-25 19:45", "Dry patches near nose.", null)
                    )

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Homepage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    sampleQueries.forEach { query ->
                        QueryCard(query)
                    }
                }

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
fun QueryCard(
    query: PatientQueryDatatype,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(query.patientName)
                Text(query.timestamp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Summary: ${query.summary}")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) Darkcontainer else Lightcontainer,
                        contentColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                    ),
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {}
                ) {
                    Text("Reject")
                }
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) Darkcontainer else Lightcontainer,
                        contentColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                    ),
                    onClick = {}
                ) {
                    Text("Accept")
                }
            }
        }
    }
}
