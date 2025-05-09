package Navigation

import UI.Homepage.Homepage
import androidx.compose.runtime.Composable
import Database.ViewModel
import UI.Chatpage.Chatpage
import UI.ConsultantPage.Consultantpage
import UI.Scanpage.Scanpage
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Homepage(viewModel, navController)
        }
        composable("scan") {
            Scanpage(viewModel, navController)
        }
        composable("consultant") {
            Consultantpage(viewModel, navController)
        }
        composable("chatpage") {
            Chatpage(viewModel, navController)
        }

    }
}