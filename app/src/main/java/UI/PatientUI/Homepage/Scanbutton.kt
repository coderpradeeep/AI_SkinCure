package UI.PatientUI.Homepage

import Database.ViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.launch

@Composable
fun Scanbutton(
    viewModel: ViewModel,
    onClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = (viewModel.isHomePage),
        label = "Scan Button",
        enter = fadeIn() + slideInHorizontally(
            initialOffsetX = {it*2}
        ),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX = {it*2}
        )

    ) {
        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(PaddingValues(horizontal = 8.dp))
                .size(60.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Pink, Darkpink)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            onClick = {
                coroutineScope.launch {
                    onClick()
                }
            }

        ) {
            Icon(
                imageVector = Icons.Outlined.DocumentScanner,
                contentDescription = "Scan",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .scale(1.2f)
            )
        }
    }
}