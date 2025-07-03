package com.appwings.testmate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RoleSelectionScreen(onRoleSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login as:", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        Button(onClick = { onRoleSelected("client") }, modifier = Modifier.fillMaxWidth()) {
            Text("Login as Tester (Become Client)")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { onRoleSelected("tester") }, modifier = Modifier.fillMaxWidth()) {
            Text("Login as Customer (Become Tester)")
        }
    }
}

