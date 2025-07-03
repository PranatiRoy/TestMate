package com.appwings.testmate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
object OTPStorage {
    var verificationId: String? = null
}

@Composable
fun OtpScreen(role: String, phone: String, onVerified: () -> Unit) {
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter OTP sent to $phone")

        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {}) {
            Text("Verify OTP")
        }
    }
}
