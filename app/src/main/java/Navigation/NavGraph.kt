package Navigation

import UI.PatientUI.Homepage.Homepage
import androidx.compose.runtime.Composable
import Database.ViewModel
import UI.LandingPage.LoginPage
import UI.LandingPage.SignUp.FirstSignupPage
import UI.LandingPage.SignUp.FourthSignupPage
import UI.LandingPage.SignUp.SecondSignupPage
import UI.LandingPage.SignUp.ThirdSignupPage
import UI.PatientUI.Chatpage.Chatpage
import UI.PatientUI.ConsultantPage.Consultantpage
import UI.PatientUI.Resultpage.Resultpage
import UI.PatientUI.Scanpage.Scanpage
import UI.Common.SplashScreenWithLoading
import UI.PatientUI.Historypage.Historypage
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        }
    ) {
        composable("splash") {
            SplashScreenWithLoading(viewModel, navController)
        }
        composable("home") {
            Homepage(viewModel, navController)
        }
        composable("consultant") {
            Consultantpage(viewModel, navController)
        }
        composable("history") {
            Historypage(viewModel, navController)
        }
        composable("scan") {
            Scanpage(viewModel, navController)
        }
        composable("chatpage") {
            Chatpage(viewModel, navController)
        }
        composable("resultpage") {
            Resultpage(viewModel, navController)
        }
        composable("loginpage") {
            LoginPage(viewModel, navController)
        }
        composable("signuppage1") {
            FirstSignupPage(viewModel, navController)
        }
        composable("signuppage2") {
            SecondSignupPage(viewModel, navController)
        }
        composable("signuppage3") {
            ThirdSignupPage(viewModel, navController)
        }
        composable("signuppage4") {
            FourthSignupPage(viewModel, navController)
        }

    }
}