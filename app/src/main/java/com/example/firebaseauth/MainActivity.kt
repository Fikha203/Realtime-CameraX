package com.example.firebaseauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.ui.create.CreateScreen
import com.example.firebaseauth.ui.home.HomeScreen
import com.example.firebaseauth.ui.login.LoginScreen
import com.example.firebaseauth.ui.theme.FirebaseAuthTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAuthTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                navController = navController
            )
        }
        composable("home") {
            HomeScreen(
                navigateBack = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }

                },
                navigateToCreate = { navController.navigate("create") }
            )
        }
        composable("create") {
            CreateScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }

}







