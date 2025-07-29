package UI.PatientUI.ConsultantPage

import Database.DataType.ConsultantDataType
import Database.ViewModel
import UI.Common.TopBar
import UI.Common.bouncyClickable
import com.example.aiskincure.R
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Whatsapp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkcontainer
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Pink

val consultants = listOf(
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),
    ConsultantDataType("Sehgal", "Skin cancer expert", R.drawable.default_profile2),

)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Consultantpage(
    viewModel: ViewModel,
    navController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.isConsultantPage = true
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isConsultantPage = false
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

                consultants?.forEach { consultant ->
                    ConsultansCard(consultant, viewModel, navController)
                }

                // Footer
                Spacer(Modifier.height(80.dp))
            }

        }
    }
}

@Composable
fun ConsultansCard(
    consultant: ConsultantDataType,
    viewModel: ViewModel,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isSystemInDarkTheme()) Darkcontainer else Lightcontainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(PaddingValues(8.dp))
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(45.dp)
                        .background(Color.Transparent)
                        //.padding(PaddingValues(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = consultant.image),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        alpha = 0.8f
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(PaddingValues(start = 8.dp))
                ) {
                    Text(
                        text = consultant.expertise,
                        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                    )
                    Text(
                        text = "Dr. ${consultant.name}",
                        color = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(PaddingValues(start = 8.dp))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Whatsapp,
                    contentDescription = null,
                    tint = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    modifier = Modifier
                        .bouncyClickable(1f) {

                        }
                        .scale(.8f)
                        .size(35.dp)
                )
                TextButton(
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pink,
                        contentColor = Color.White
                    ),
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 8.dp))
                ) {
                    Text(
                        text = "Book",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        maxLines = 1,
                        modifier = Modifier
                    )
                }
            }

        }
    }
}