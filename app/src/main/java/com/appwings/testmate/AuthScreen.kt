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
fun AuthScreen(role: String, onAuthSuccess: () -> Unit) {
    val context = LocalContext.current
    var phone by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                Firebase.firestore.collection("users").document(uid)
                    .set(mapOf("role" to role)).addOnSuccessListener {
                        onAuthSuccess()
                    }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail().build()
            val client = GoogleSignIn.getClient(context, gso)
            launcher.launch(client.signInIntent)
        }) {
            Text("Sign in with Google")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Enter Phone Number") },
            placeholder = { Text("+91XXXXXXXXXX") }
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(context as Activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(cred: PhoneAuthCredential) {
                        // Optional auto sign-in
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        OTPStorage.verificationId = verificationId
                        navController(context).navigate("otp_screen/$role/$phone")
                    }
                }).build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }) {
            Text("Continue with Phone OTP")
        }
    }
}
