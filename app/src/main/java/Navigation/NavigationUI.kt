package Navigation

import Database.ViewModel
import UI.Common.bouncyClickable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBar(
    viewModel: ViewModel,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val scope = rememberCoroutineScope()
    val isPatient = viewModel.userProfileInfo.collectAsStateWithLifecycle().value?.isPatient

    val rootPath by remember {
        derivedStateOf {
            if (isPatient != false) {
                "home"
            }
            else {
                "home2"
            }
        }
    }

    val navItems by remember {
        derivedStateOf {
            if (isPatient != false) {
                patientNavItems
            } else {
                dermoNavItems
            }
        }
    }

    val visibility by remember {
        derivedStateOf {
            viewModel.isHomePage || viewModel.isConsultantPage || viewModel.isHistoryPage
        }
    }

    AnimatedVisibility(
        visible = visibility,
        label = "Bottom navigation bar",
        enter = fadeIn() + slideInVertically(
            initialOffsetY = {it}
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = {it}
        )

    ) {
        // Buttons row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .border(
                    width = 0.3.dp,
                    color = Color.DarkGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .background(
                    color = if (isSystemInDarkTheme()) DarkBG else BG,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(PaddingValues(vertical = 8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            // Navigation buttons and Navigation control
            navItems.forEach { item ->
                val isSelected = item.title.lowercase() == navBackStackEntry?.destination?.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 8.dp, vertical = 2.dp))
                        .width(70.dp)
                        .height(50.dp)
                        .bouncyClickable(0.97f) {
                            scope.launch {
                                navController.navigate(item.title.lowercase()) {
                                    popUpTo(rootPath) {
                                        inclusive = false
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    // Buttons
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if (isSystemInDarkTheme()) {
                            if (isSelected) Color.LightGray else Color.Gray
                        } else {
                            if (isSelected) Color.Black else Color.Gray
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    AnimatedVisibility(
                        visible = isSelected,
                        label = "Animated visibility"
                    ) {
                        Text(
                            text = item.title,
                            color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                            modifier = Modifier,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            letterSpacing = 0.sp
                        )
                    }
                }

            }
        }


    }
}