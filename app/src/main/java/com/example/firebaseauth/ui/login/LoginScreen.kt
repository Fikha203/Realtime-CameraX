package com.example.firebaseauth.ui.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebaseauth.R
import com.example.firebaseauth.data.Response

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory(LocalContext.current))
) {
    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        when (authState) {
            is Response.Loading -> {

                GoogleSignInButton(onClick = { viewModel.signInWithGoogle() }, isEnable = false)
            }

            is Response.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }

                    }
                }
            }

            is Response.Error -> {
                Log.d("LoginScreen", "Login Gagal: ${(authState as Response.Error).errorMessage}")
                GoogleSignInButton(onClick = { viewModel.signInWithGoogle() }, isEnable = true)

            }

            else -> {}
        }
    }
}

@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier, onClick: () -> Unit, isEnable: Boolean) {
    Button(
        onClick = {
            onClick()
        },
        enabled = isEnable,
        modifier = modifier.padding(16.dp)

    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign In with Google")
            Spacer(modifier = Modifier.width(8.dp))

            if(!isEnable) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }


    }

}
