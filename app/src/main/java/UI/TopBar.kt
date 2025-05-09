package UI

import Database.ViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG

@Composable
fun TopBar(
    viewModel: ViewModel,
    navController: NavHostController,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) DarkBG else BG)
            .padding(PaddingValues(vertical = 8.dp))
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .weight(1f)
                .padding(PaddingValues(start = 16.dp))
        ) {
            if (!viewModel.isHomePage) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Back",
                    modifier = Modifier
                        .scale(1.2f)
                        .padding(PaddingValues(end = 16.dp))
                        .rotate(-90f)
                        .bouncyClickable(1f) {
                            navController.popBackStack()
                        }
                )
            }
            Text(
                text = "AI SkinCure",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More",
            modifier = Modifier
                .padding(PaddingValues(horizontal = 16.dp))
                .clickable {
                    // TODO
                }
        )
    }
}