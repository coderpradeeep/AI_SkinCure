package Navigation

import UI.Homepage.Homepage
import androidx.compose.runtime.Composable
import Database.ViewModel
import UI.Chatpage.Chatpage
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavHost(
    rootNavController: NavHostController,
    startDestination: String,
    viewModel: ViewModel
) {
    NavHost(navController = rootNavController, startDestination = startDestination) {
        composable("home") {
            HomeNavHost(viewModel)
        }
        composable("scan") {
            ScanNavHost(viewModel)
        }
        composable("consultant") {
            ConsultantNavHost(viewModel)
        }

    }
}

@Composable
fun HomeNavHost(viewModel: ViewModel) {
    val homeNavController = rememberNavController()

    NavHost(
        homeNavController, startDestination = "home"
    ) {
        composable("home" ) {
            Homepage(viewModel, homeNavController)
        }
        composable("Chatpage") {
            Chatpage(viewModel)
        }
    }
}

@Composable
fun ScanNavHost(viewModel: ViewModel) {
    val scanNavController = rememberNavController()

    NavHost(
        scanNavController, startDestination = "scan"
    ) {
        composable("scan" ) {
            // TODO
        }

    }
}

@Composable
fun ConsultantNavHost(viewModel: ViewModel) {
    val consultantNavController = rememberNavController()

    NavHost(
        consultantNavController, startDestination = "consultant"
    ) {
        composable("consultant" ) {
            // TODO
        }

    }
}