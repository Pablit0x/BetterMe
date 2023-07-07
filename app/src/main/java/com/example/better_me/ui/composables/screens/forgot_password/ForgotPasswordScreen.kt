package com.example.better_me.ui.composables.screens.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.better_me.R
import com.example.better_me.ui.composables.reusable_components.EmailField
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
private lateinit var firebaseAuth: FirebaseAuth

/**
 * This composable function creates the UI for the forgot password screen
 * @param navController Navigation controller to navigate between screens
 */
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    forgotPasswordViewModel = ForgotPasswordViewModel()
    firebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val email: MutableState<String> = remember { mutableStateOf("") }
    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(PerfectGray)
        ) {
            Image(
                painter = key(R.drawable.splash_screen) { painterResource(R.drawable.splash_screen) },
                contentDescription = "app logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(330.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(PerfectGray)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(NavyBlue)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp,
                color = White
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = stringResource(id = R.string.forgot_password_description),
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = White
            )
            Spacer(modifier = Modifier.padding(15.dp))
            EmailField(email, emailError)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                onClick = {
                    emailError.value = !forgotPasswordViewModel.validateEmail(email = email.value)
                    if (!emailError.value) {
                        forgotPasswordViewModel.sendPasswordResetEmail(
                            email = email.value,
                            context = context,
                            navController = navController,
                            firebaseAuth = firebaseAuth
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.submit),
                    fontSize = 20.sp,
                    color = Black
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(id = R.string.back_to_sign_in),
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier.clickable { navController.popBackStack() })
            }
        }
    }
}