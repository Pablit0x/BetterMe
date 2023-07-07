package com.example.better_me.ui.composables.screens.sign_up

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.better_me.R
import com.example.better_me.ui.composables.reusable_components.EmailField
import com.example.better_me.ui.composables.reusable_components.PasswordField
import com.example.better_me.ui.composables.reusable_components.UserTypeField
import com.example.better_me.ui.composables.reusable_components.UsernameField
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var signUpViewModel: SignUpViewModel

/**
 * This composable function displays the sign-up UI
 * @param navController Navigation controller to navigate between screens
 */

@Composable
fun SignUpScreen(navController: NavController) {
    firebaseAuth = FirebaseAuth.getInstance()
    signUpViewModel = SignUpViewModel()
    val context = LocalContext.current

    val username: MutableState<String> = remember { mutableStateOf("") }
    val userType: MutableState<String> = remember { mutableStateOf("") }
    val email: MutableState<String> = remember { mutableStateOf("") }
    val password: MutableState<String> = remember { mutableStateOf("") }

    val usernameError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val userTypeError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .background(color = PerfectGray)
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
            modifier = Modifier
                .background(PerfectGray)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(NavyBlue)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = stringResource(id = R.string.sign_up),
                color = White,
                fontFamily = FontFamily.Monospace,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.padding(15.dp))
            UsernameField(username = username, isError = usernameError)
            Spacer(modifier = Modifier.padding(5.dp))
            UserTypeField(userType = userType, isError = userTypeError)
            Spacer(modifier = Modifier.padding(5.dp))
            EmailField(email = email, isError = emailError)
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(password = password, isError = passwordError)
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = {
                    emailError.value = !signUpViewModel.validateEmail(email = email.value)
                    passwordError.value =
                        !signUpViewModel.validatePassword(password = password.value)
                    usernameError.value =
                        !signUpViewModel.validateUsername(username = username.value)
                    userTypeError.value =
                        !signUpViewModel.validateUserType(userType = userType.value)

                    if (!usernameError.value && !userTypeError.value && !emailError.value && !passwordError.value) {
                        signUpViewModel.firebaseSignUp(
                            firebaseAuth = firebaseAuth,
                            username = username.value,
                            email = email.value,
                            userType = userType.value,
                            password = password.value,
                            navController = navController,
                            context = context
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(White)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
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