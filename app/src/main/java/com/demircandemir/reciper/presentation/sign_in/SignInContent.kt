package com.demircandemir.reciper.presentation.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demircandemir.reciper.R

@Composable
fun SignInScreenContent(
    onGoogleSignInClick: () -> Unit,
    onEmailClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val image: Painter = painterResource(id = R.drawable.log_in_background)

        Image(
            painter = image,
            contentDescription = "Log in background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "Reciper",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.TopCenter),
            color = Color.White
        )




        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SignInButtons(onGoogleClick = onGoogleSignInClick, onEmailClick = onEmailClicked)


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an account yet?", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                TextButton(onClick = { onRegisterClicked() }) {
                    Text(
                        text = "Register",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(
                            0xFF12964C
                        )
                    )
                }
            }
        }








    }
}


@Composable
fun SignInButtons(
    onGoogleClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onGoogleClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0FB95A),
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "CONTINUE WITH GOOGLE")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = onEmailClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0FB95A),
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.mail),
                contentDescription = "Mail Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))

            Text(text = "CONTINUE WITH EMAIL")
        }
    }
}

@Composable
@Preview(showBackground = false)
fun SignInButtonsPreview() {
    SignInButtons(onGoogleClick = {}, onEmailClick = {})
}

@Composable
@Preview(showBackground = true)
fun SignInScreenContentPreview() {
    SignInScreenContent(
        onGoogleSignInClick = {},
        onEmailClicked = {},
        onRegisterClicked = {}
    )
}





