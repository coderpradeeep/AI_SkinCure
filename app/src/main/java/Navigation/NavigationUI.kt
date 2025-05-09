package Navigation

import Database.ViewModel
import UI.bouncyClickable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Orange
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlin.coroutines.CoroutineContext

@Composable
fun BottomNavigationBar(
    viewModel: ViewModel,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
//    val context = LocalContext.current
//    var scope = CoroutineScope(context as CoroutineContext)

    AnimatedVisibility(
        visible = (!viewModel.isScanpage && viewModel.isChatPage),
        label = "Bottom navigation bar",
        enter = slideInVertically(
            initialOffsetY = {it*2}
        ),
        exit = slideOutVertically(
            targetOffsetY = {it*2}
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        if(isSystemInDarkTheme()) DarkBG else BG
                    ),
                    startY = 0f,
                    endY = 40f
                    )
                )
                .padding(PaddingValues(vertical = 6.dp, horizontal = 4.dp))
                //.navigationBarsPadding()
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(rememberScrollState())
        ) {
            // Navigation buttons and Navigation control
            navItems.forEach { item ->
                val isSelected = item.title.lowercase() == navBackStackEntry?.destination?.route

                if (!item.isAddButton) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(PaddingValues(horizontal = 8.dp, vertical = 2.dp))
                            .width(70.dp)
//                        .fillMaxWidth()
                            .height(50.dp)
                            .bouncyClickable(0.97f) {
                                viewModel.viewModelScope.launch {
                                    navController.navigate(item.title.lowercase()) {

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
                                color = if(isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                modifier = Modifier,
                                fontWeight = if (isSelected) FontWeight.W900 else FontWeight.W500,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                lineHeight = 1.sp,
                                letterSpacing = 0.sp
                            )
                        }
                    }
                } else {
                    ElevatedCard(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 16.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(PaddingValues(horizontal = 12.dp))
                            .shadow(16.dp, spotColor = Darkpink, shape = CircleShape)
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Pink, Darkpink)
                                ),
                                shape = CircleShape
                            ),

                        onClick = {
                            viewModel.viewModelScope.launch {
                                navController.navigate(item.title.lowercase()) {

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }

                    ) {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.title,
                            //tint = Color.Black,
                            modifier = Modifier
                                //.shadow(10.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxHeight()
                                .scale(1.2f)

                        )
                    }
                }

            }
        }

    }
}