package com.appwings.testmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.appwings.testmate.ui.theme.TestMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    val user = auth.currentUser
                    if (user != null) {
                        Firebase.firestore.collection("users").document(user.uid).get()
                            .addOnSuccessListener { doc ->
                                val role = doc.getString("role")
                                when (role) {
                                    "client" -> navController.navigate("client_home")
                                    "tester" -> navController.navigate("tester_home")
                                    else -> navController.navigate("role_selection")
                                }
                            }
                    }
                }

                AppNavHost(navController)
            }
        }
    }
}




