package UI.Common

import Database.ViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    viewModel: ViewModel,
    navController: NavHostController,
    title: String = "AI SkinCure",
) {
    val scope = rememberCoroutineScope()

    val enableIcon by remember {
        derivedStateOf {
            !viewModel.isHomePage
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) DarkBG else BG)
            .padding(PaddingValues(vertical = 8.dp))
            .statusBarsPadding()
            .animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .weight(1f)
                .padding(PaddingValues(start = 16.dp))
                .animateContentSize()
        ) {
            AnimatedVisibility(
                visible = enableIcon,
                label = "Topbar back nav Icon",
                enter = fadeIn() + slideInHorizontally(
                    initialOffsetX = {it}
                ),
                exit = fadeOut() + slideOutHorizontally(
                    targetOffsetX = {it}
                )

            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Back",
                    modifier = Modifier
                        .scale(1.2f)
                        .padding(PaddingValues(end = 16.dp))
                        .rotate(-90f)
                        .bouncyClickable(1f) {
                            scope.launch {
                                navController.popBackStack()
                            }
                        }
                )
            }
            Text(
                text = title,
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
                    // TODO Add drop down menu
                }
        )
    }
}