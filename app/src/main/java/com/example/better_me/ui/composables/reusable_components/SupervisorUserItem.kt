package com.example.better_me.ui.composables.reusable_components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.better_me.R
import com.example.better_me.data.model.SupervisorUser
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.White

/**
 * This composable function represents how each supervisor user-item inside the lazy column will look.
 * @param user User object that will be displayed
 */

@Composable
fun SupervisorUserItem(user: SupervisorUser) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(5.dp)
            .border(width = 1.dp, color = White, shape = RoundedCornerShape(12.dp)),
        color = White
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(48.dp),
                    shape = CircleShape,
                    elevation = 2.dp
                ) {
                    val image: Painter = if (user.avatarID == null) {
                        painterResource(id = R.drawable.ic_person)
                    } else {
                        painterResource(avatarList[user.avatarID])
                    }
                    Image(
                        painter = image, contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(48.dp)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(4f)
                ) {
                    Text(
                        text = user.username.toString(),
                        textAlign = TextAlign.Start,
                        color = Black,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxHeight()
                    )

                    Text(
                        text = user.email.toString(),
                        textAlign = TextAlign.Start,
                        color = Black,
                        fontSize = 12.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }
                IconButton(onClick = {
                    val uri = Uri.parse("mailto:${user.email}")
                        .buildUpon()
                        .build()
                    val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
                    startActivity(context, emailIntent, null)
                }) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email icon",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(36.dp)
                    )
                }
            }
        }
    }

}