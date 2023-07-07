package com.example.better_me.ui.composables.reusable_components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.better_me.R
import com.example.better_me.data.screens.Screen
import com.example.better_me.ui.composables.screens.select_avatar.AvatarViewModel
import com.example.better_me.ui.composables.screens.supervisor.main.setAvatarDialog
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()
private lateinit var avatarViewModel: AvatarViewModel

/**
 * This composable function displays the top bar designed for the Supervisor Users
 * @param navController Navigation controller used to navigate between screens
 * @param title String variable that represents the title of the TopBar
 * @param isHome Indicates if the current destination is a home destination
 */
@Composable
fun SupervisorUserTopBar(
    navController: NavController,
    title: String,
    isHome: Boolean,
    avatarID: Int
) {
    avatarViewModel = AvatarViewModel()
    var avatar by remember { mutableStateOf(R.drawable.ic_person) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center, color = White) },
        backgroundColor = PerfectGray,
        navigationIcon =
        {
            IconButton(onClick = { navController.navigateUp() }) {
                if (isHome && firebaseAuth.currentUser != null) {
                    avatar = if (avatarID == -1) {
                        R.drawable.ic_person
                    } else {
                        avatarList[avatarID]
                    }
                    Image(
                        painter = painterResource(id = avatar),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(45.dp)
                            .clickable {
                                setAvatarDialog(true)
                            }
                    )
//                    AnimatedAvatarImage(imageResource = avatar)

                } else if (navController.previousBackStackEntry != null) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }
        },
        actions = {

            IconButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                    Toast.makeText(
                        context,
                        context.getString(R.string.logged_out_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    firebaseAuth.signOut()
                },
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
            ) {
                Icon(Icons.Default.ExitToApp, "Logout", tint = White)
            }
        }
    )
}