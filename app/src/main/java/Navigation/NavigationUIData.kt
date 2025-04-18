package Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SettingsOverscan
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val isAddButton: Boolean = false
)

val navItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = "Scan",
        selectedIcon = Icons.Outlined.DocumentScanner,
        unselectedIcon = Icons.Outlined.DocumentScanner,
        isAddButton = true
    ),
    BottomNavigationItem(
        title = "Consultant",
        selectedIcon = Icons.Filled.HealthAndSafety,
        unselectedIcon = Icons.Outlined.HealthAndSafety
    )
)
