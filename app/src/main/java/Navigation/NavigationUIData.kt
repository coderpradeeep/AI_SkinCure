package Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SettingsOverscan
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.History
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val patientNavItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Rounded.GridView,
        unselectedIcon = Icons.Outlined.GridView
    ),
    BottomNavigationItem(
        title = "Consultant",
        selectedIcon = Icons.Rounded.HealthAndSafety,
        unselectedIcon = Icons.Outlined.HealthAndSafety
    ),
    BottomNavigationItem(
        title = "history",
        selectedIcon = Icons.Rounded.History,
        unselectedIcon = Icons.Rounded.History,
    )
)

val dermoNavItems = listOf(
    BottomNavigationItem(
        title = "Home2",
        selectedIcon = Icons.Rounded.GridView,
        unselectedIcon = Icons.Outlined.GridView
    ),
    BottomNavigationItem(
        title = "consultations",
        selectedIcon = Icons.Rounded.ChatBubble,
        unselectedIcon = Icons.Rounded.ChatBubbleOutline
    ),
    BottomNavigationItem(
        title = "history2",
        selectedIcon = Icons.Rounded.History,
        unselectedIcon = Icons.Rounded.History,
    )
)
