package UI.LandingPage

import Database.ViewModel
import UI.Common.bouncyClickable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.aiskincure.R
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Pink
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val userProfile by viewModel.userProfileInfo.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Darkpink, Color.Black),
                    startY = 0f,
                    endY = 2500f
                )
            ),
        color = Color.Transparent,

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .animateContentSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Spacer(Modifier.height(24.dp))

                // Guest login button
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray.copy(alpha = 0.2f),
                        ),
                        //border = BorderStroke(0.5.dp, Color.LightGray),
                    ) {
                        Row(
                            modifier = Modifier
                                .bouncyClickable(1f) {
                                    viewModel.setGuestMode()

                                    navController.navigate("signuppage1")

                                    // Firebase test
                                    Firebase.analytics.logEvent("guest_login", null)
                                }
                                .wrapContentWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Guest",
                                color = Color.LightGray,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                lineHeight = 12.sp,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }

                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val textFieldFocusManager = LocalFocusManager.current

                // Center Column: Email/Password + Buttons
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var email by remember { mutableStateOf(userProfile?.email) }
                    var isEmailTouched by remember { mutableStateOf(false) }
                    var emailValid by remember { mutableStateOf(true) }
                    var emailTypingJob by remember { mutableStateOf<Job?>(null) }

                    OutlinedTextField(
                        value = email ?: "",
                        onValueChange = {
                            email = it
                            if (!isEmailTouched) isEmailTouched = true
                            emailValid = true // <--- Immediately hide the error when typing

                            // Cancel previous job and start a new debounce validation
                            emailTypingJob?.cancel()
                            emailTypingJob = CoroutineScope(Dispatchers.Default).launch {
                                delay(500) // debounce delay
                                emailValid = isValidMail(email!!)
                            }
                        },
                        label = {
                            Text(
                                "Email",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        isError = isEmailTouched && !emailValid,
                        supportingText = {
                            if(isEmailTouched && !emailValid) {
                                Text(
                                    text = "Invalid mail id",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    maxLines = 2
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .animateContentSize(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                textFieldFocusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Pink.copy(alpha = 0.6f),
                            cursorColor = Lightcontainer,
                        )
                    )

                    var passwordVisible by remember { mutableStateOf(false) }
                    var password by remember { mutableStateOf(userProfile?.password) }
                    var isPasswordTouched by remember { mutableStateOf(false) }
                    var passwordValid by remember { mutableStateOf(true) }
                    var passwordTypingJob by remember { mutableStateOf<Job?>(null) }

                    OutlinedTextField(
                        value = password ?: "",
                        onValueChange = {
                            password = it
                            if (!isPasswordTouched) isPasswordTouched = true
                            passwordValid = true // <--- Immediately hide the error when typing

                            // Cancel previous job and start a new debounce validation
                            passwordTypingJob?.cancel()
                            passwordTypingJob = CoroutineScope(Dispatchers.Default).launch {
                                delay(500) // debounce delay
                                passwordValid = isValidPassword(password!!)
                            }
                        },
                        label = {
                            Text(
                                "Password",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .bouncyClickable(1f) {
                                        passwordVisible = !passwordVisible
                                    }
                            )
                        },
                        isError = isPasswordTouched && !passwordValid,
                        supportingText = {
                            if(isPasswordTouched && !passwordValid) {
                                Text(
                                    text = "Invalid password (Minimum 8 length, At least 1 uppercase, number, special character)",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    maxLines = 2
                                )
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .animateContentSize(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                textFieldFocusManager.clearFocus()
                                // TODO Authenticate
                            }
                        ),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Pink.copy(alpha = 0.6f),
                            cursorColor = Lightcontainer,
                        )
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        enabled = isValidMail(email ?: "") && isValidPassword(password ?: ""),
                        onClick = {
                            // TODO Authenticate
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black,
                            containerColor = Color.LightGray,
                            disabledContentColor = Color.Black.copy(alpha = 0.6f),
                            disabledContainerColor = Color.LightGray.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            "Login",
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate("signuppage1")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.LightGray,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Sign up",
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    // forgot password
                    Text(
                        text = "Forgot password ?",
                        color = Pink,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .bouncyClickable(0.95f) {
                                // TODO
                            }
                            .padding(4.dp)
                    )

                    // Login helper buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black,
                            ),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            modifier = Modifier
                                .size(30.dp)

                        ) {
                            Image(
                                painter = painterResource(R.drawable.google),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clickable() {
                                    // TODO
                                }
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black,
                            ),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            modifier = Modifier
                                .size(30.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.facebook),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                colorFilter = ColorFilter.tint(
                                    color = Color.Black,
                                    blendMode = BlendMode.Difference
                                ),
                                modifier = Modifier
                                    .clickable() {
                                        // TODO
                                    }
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black,
                            ),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            modifier = Modifier
                                .size(30.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.twitter),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clickable() {
                                        // TODO
                                    }
                            )
                        }

                    }
                }

                // Footer
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

fun isValidPassword(password : String) : Boolean {
    if (password.length < 8) return false
    if (!password.any { it.isUpperCase() }) return false
    if (!password.any { it.isLowerCase() }) return false
    if (!password.any { it.isDigit() }) return false
    if (!password.any { "!@#\$%^&*()-_=+[{]}|;:'\",<.>/?".contains(it) }) return false

    return true
}

fun isValidMail(email : String) : Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidName(name: String) : Boolean {
    return name.length >= 2 && name.all { it.isLetter() || it.isWhitespace() }
}