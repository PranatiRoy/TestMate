package com.appwings.testmate



import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    NavHost(navController, startDestination = "role_selection") {
        composable("role_selection") {
            RoleSelectionScreen { role ->
                selectedRole = role
                navController.navigate("auth_screen")
            }
        }

        composable("auth_screen") {
            selectedRole?.let { role ->
                AuthScreen(role = role) {
                    navController.navigate(if (role == "client") "client_home" else "tester_home") {
                        popUpTo("role_selection") { inclusive = true }
                    }
                }
            }
        }

        composable("otp_screen/{role}/{phone}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            OtpScreen(role, phone) {
                navController.navigate(if (role == "client") "client_home" else "tester_home") {
                    popUpTo("role_selection") { inclusive = true }
                }
            }
        }

        composable("client_home") { ClientHomeScreen() }
        composable("tester_home") { TesterHomeScreen() }
    }
}

