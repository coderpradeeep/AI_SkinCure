package UI.LandingPage.SignUp

import Database.ViewModel
import UI.LandingPage.isValidMail
import UI.LandingPage.isValidName
import UI.LandingPage.isValidPassword
import UI.Common.bouncyClickable
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Pink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FirstSignupPage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfileInfo.collectAsStateWithLifecycle()

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
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize()
        ) {
            Text(
                text = "Continue as",
                fontWeight = FontWeight.W900,
                fontSize = 36.sp,
                letterSpacing = 0.sp,
                color = Pink,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.updateUserProfile {
                        it.copy(isPatient = true)
                    }

                    if (userProfile?.isGuest == true) {
                        viewModel.setUserProfile()

                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate("signuppage2")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Patient",
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.updateUserProfile {
                        it.copy(isPatient = false)
                    }

                    if(userProfile?.isGuest == true) {
                        viewModel.setUserProfile()

                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true } // Clears everything in backstack
                            launchSingleTop = true // Prevents multiple instances of "home"
                        }
                    }
                    else {
                        navController.navigate("signuppage2")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.LightGray,
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Dermatologist",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }

            // Footer
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun SecondSignupPage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val userProfile by viewModel.userProfileInfo.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Darkpink, Color.Black),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        color = Color.Transparent,

        ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .animateContentSize()
        ) {

            // Text fields (Name, email, password)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val textFieldFocusManager = LocalFocusManager.current

                var name by remember { mutableStateOf(userProfile?.name) }
                LaunchedEffect(userProfile?.name) {
                    name = userProfile?.name
                }

                var isNameTouched by remember { mutableStateOf(false) }
                var nameValid by remember { mutableStateOf(true) }
                var nameTypingJob by remember { mutableStateOf<Job?>(null) }

                OutlinedTextField(
                    value = name ?: "",
                    onValueChange = {
                        name = it
                        if (!isNameTouched) isNameTouched = true
                        nameValid = true // <--- Immediately hide the error when typing

                        // Cancel previous job and start a new debounce validation
                        nameTypingJob?.cancel()
                        nameTypingJob = CoroutineScope(Dispatchers.Default).launch {
                            delay(500) // debounce delay
                            nameValid = isValidName(name!!)
                        }
                    },
                    label = {
                        Text(
                            "Full Name",
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
                    isError = isNameTouched && !nameValid,
                    supportingText = {
                        if(isNameTouched && !nameValid) {
                            Text(
                                text = "Invalid Name (letters only)",
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

                var email by remember { mutableStateOf(userProfile?.email) }
                LaunchedEffect(userProfile?.email) {
                    email = userProfile?.email ?: ""
                }

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

                //Spacer(modifier = Modifier.height(8.dp))

                var password by remember { mutableStateOf(userProfile?.password) }
                LaunchedEffect(userProfile?.password) {
                    password = userProfile?.password ?: ""
                }

                var passwordVisible by remember { mutableStateOf(false) }
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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.LightGray,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Back",
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        enabled = isValidName(name ?: "") && isValidMail(email ?: "") && isValidPassword(password ?: ""),
                        onClick = {
                            viewModel.updateUserProfile {
                                it.copy(
                                    name = name,
                                    email = email,
                                    password = password
                                )
                            }

                            navController.navigate("signuppage3")
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black,
                            containerColor = Color.LightGray,
                            disabledContentColor = Color.Black.copy(alpha = 0.6f),
                            disabledContainerColor = Color.LightGray.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            "Next",
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    }
                }
                // Footer
                Spacer(modifier = Modifier.height(60.dp))
            }

        }
    }
}

val ageRange = (1..120).toList()

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdSignupPage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val userProfile by viewModel.userProfileInfo.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Darkpink, Color.Black),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        color = Color.Transparent,

        ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
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
                                    if (userProfile?.isPatient == true) {
                                        viewModel.setUserProfile()

                                        navController.navigate("home") {
                                            popUpTo(0) {
                                                inclusive = true
                                            } // Clears everything in backstack
                                            launchSingleTop =
                                                true // Prevents multiple instances of "home"
                                        }
                                    } else navController.navigate("signuppage4")
                                }
                                .wrapContentWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Skip",
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

            // Text fields (Age, Gender)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val textFieldFocusManager = LocalFocusManager.current

                var age by remember { mutableStateOf(userProfile?.age) }
                LaunchedEffect(userProfile?.age) {
                    age = userProfile?.age
                }

                var ageExpanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = ageExpanded,
                    onExpandedChange = {
                        ageExpanded = !ageExpanded
                    }
                ) {
                    // Anchor to show selected age range
                    OutlinedTextField(
                        readOnly = true,
                        value = (age ?: 0).toString(),
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                age = it.toInt()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        label = {
                            if(age == null) {
                                Text(
                                    text = "Age",
                                    color = Color.LightGray,
                                    maxLines = 1,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = ageExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Pink.copy(alpha = 0.6f),
                            cursorColor = Lightcontainer,
                            focusedTextColor = Color.LightGray,
                            unfocusedTextColor = Color.LightGray
                        )
                    )
                    // Age ranges
                    DropdownMenu(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(0.8f),
                        expanded = ageExpanded,
                        containerColor = Color.Black,
                        onDismissRequest = {
                            ageExpanded = false
                        }
                    ) {
                        ageRange.forEach { range ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .fillMaxWidth(),
                                text = {
                                    Text(
                                        text = range.toString(),
                                        color = Color.LightGray,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 12.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                },
                                colors = MenuDefaults.itemColors().copy(
                                    textColor = Color.LightGray,
                                ),
                                onClick = {
                                    age = range

                                    textFieldFocusManager.clearFocus()
                                    ageExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                var gender by remember { mutableStateOf(userProfile?.gender) }
                LaunchedEffect(userProfile?.gender) {
                    gender = userProfile?.gender
                }

                val genders = listOf("Male", "Female", "Other")
                var genderExpanded by remember { mutableStateOf(false) }

                // gender
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = genderExpanded,
                    onExpandedChange = {
                        genderExpanded = !genderExpanded
                    }
                ) {
                    // Anchor to show selected age range
                    OutlinedTextField(
                        readOnly = true,
                        value = gender ?: "",
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                gender = it
                            }
                        },
                        label = {
                            if(gender.isNullOrEmpty()) {
                                Text(
                                    text = "Gender",
                                    color = Color.LightGray,
                                    maxLines = 1,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Pink.copy(alpha = 0.6f),
                            cursorColor = Lightcontainer,
                            focusedTextColor = Color.LightGray,
                            unfocusedTextColor = Color.LightGray
                        )
                    )
                    // Age ranges
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        expanded = genderExpanded,
                        onDismissRequest = {
                            genderExpanded = false
                        },
                        containerColor = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        genders.forEach { item ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .fillMaxWidth(),
                                text = {
                                    Text(
                                        text = item,
                                        color = Color.LightGray,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 12.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                },
                                colors = MenuDefaults.itemColors().copy(
                                    textColor = Color.LightGray,
                                ),
                                onClick = {
                                    gender = item

                                    textFieldFocusManager.clearFocus()
                                    genderExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.LightGray,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Back",
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        enabled = age != null && gender != null,
                        onClick = {
                            viewModel.updateUserProfile {
                                it.copy(gender = gender, age = age)
                            }

                            if (userProfile?.isPatient == true) {
                                viewModel.setUserProfile()

                                navController.navigate("home") {
                                    popUpTo(0) {
                                        inclusive = true
                                    } // Clears everything in backstack
                                    launchSingleTop =
                                        true // Prevents multiple instances of "home"
                                }
                            } else navController.navigate("signuppage4")
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black,
                            containerColor = Color.LightGray,
                            disabledContentColor = Color.Black.copy(alpha = 0.6f),
                            disabledContainerColor = Color.LightGray.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            text = if(userProfile?.isPatient == true) "Save Profile" else "Next",
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    }
                }
                // Footer
                Spacer(modifier = Modifier.height(60.dp))

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FourthSignupPage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val userProfile by viewModel.userProfileInfo.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Darkpink, Color.Black),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        color = Color.Transparent,

        ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
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
                                    viewModel.setUserProfile()
                                    // TODO Update when Dermo's screens are ready
                                    navController.navigate("home") {
                                        popUpTo(0) { inclusive = true } // Clears everything in backstack
                                        launchSingleTop = true // Prevents multiple instances of "home"
                                    }
                                }
                                .wrapContentWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Skip",
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

            // Text fields (specialization, experience, licence no.)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val textFieldFocusManager = LocalFocusManager.current

                var specialization by remember { mutableStateOf(userProfile?.specialization) }
                LaunchedEffect(userProfile?.specialization) {
                    specialization = userProfile?.specialization
                }

                var speExpanded by remember { mutableStateOf(false) }
                val specializations = listOf(
                    "Medical Dermatology",
                    "Surgical Dermatology",
                    "Cosmetic Dermatology",
                    "Pediatric Dermatology",
                    "Photo Dermatology",
                    "Contact/Allergic Dermatology"
                )

                // gender
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = speExpanded,
                    onExpandedChange = {
                        speExpanded = !speExpanded
                    }
                ) {
                    // Anchor to show selected age range
                    OutlinedTextField(
                        readOnly = true,
                        value = specialization ?: "",
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = {},
                        label = {
                            if(specialization.isNullOrEmpty()) {
                                Text(
                                    text = "Specialization",
                                    color = Color.LightGray,
                                    maxLines = 1,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = speExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Pink.copy(alpha = 0.6f),
                            cursorColor = Lightcontainer,
                            focusedTextColor = Color.LightGray,
                            unfocusedTextColor = Color.LightGray
                        )
                    )
                    // Age ranges
                    DropdownMenu(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(0.8f),
                        expanded = speExpanded,
                        onDismissRequest = {
                            speExpanded = false
                        },
                        containerColor = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        specializations.forEach { item ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .fillMaxWidth(),
                                text = {
                                    Text(
                                        text = item,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 12.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                },
                                colors = MenuDefaults.itemColors().copy(
                                    textColor = Color.LightGray
                                ),
                                onClick = {
                                    specialization = item

                                    textFieldFocusManager.moveFocus(FocusDirection.Down)
                                    speExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))

                var expYears by remember { mutableStateOf(userProfile?.experienceYears) }
                LaunchedEffect(userProfile?.experienceYears) {
                    expYears = userProfile?.experienceYears
                }

                var expYearsTouched by remember { mutableStateOf(false) }
                var expYearsValid by remember { mutableStateOf(true) }
                var expYearsTypingJob by remember { mutableStateOf<Job?>(null) }

                OutlinedTextField(
                    value = (expYears ?: "").toString(),
                    onValueChange = {
                        if (it.isNotEmpty()) {
                            expYears = it.toInt()
                        }
                        if (!expYearsTouched) expYearsTouched = true
                        expYearsValid = true // <--- Immediately hide the error when typing

                        // Cancel previous job and start a new debounce validation
                        expYearsTypingJob?.cancel()
                        expYearsTypingJob = CoroutineScope(Dispatchers.Default).launch {
                            delay(500) // debounce delay
                            expYearsValid = isValidExperience(expYears!!)
                        }
                    },
                    label = {
                        Text(
                            "Experience (years)",
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    isError = expYearsTouched && !expYearsValid,
                    supportingText = {
                        if(expYearsTouched && !expYearsValid) {
                            Text(
                                text = "Invalid experience (accepted 1 - 60)",
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


                var licence by remember { mutableStateOf(userProfile?.licenseNumber) }
                LaunchedEffect(userProfile?.licenseNumber) {
                    licence = userProfile?.licenseNumber
                }

                var licenceVisible by remember { mutableStateOf(false) }
                var licenceTouched by remember { mutableStateOf(false) }
                var licenceValid by remember { mutableStateOf(true) }
                var licenceTypingJob by remember { mutableStateOf<Job?>(null) }

                OutlinedTextField(
                    value = licence ?: "",
                    onValueChange = {
                        licence = it
                        if (!licenceTouched) licenceTouched = true
                        licenceValid = true // <--- Immediately hide the error when typing

                        // Cancel previous job and start a new debounce validation
                        licenceTypingJob?.cancel()
                        licenceTypingJob = CoroutineScope(Dispatchers.Default).launch {
                            delay(500) // debounce delay
                            licenceValid = isValidLicenceNumber(licence!!)
                        }
                    },
                    label = {
                        Text(
                            "Licence Number",
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    isError = licenceTouched && !licenceValid,
                    supportingText = {
                        if(licenceTouched && !licenceValid) {
                            Text(
                                text = "Invalid Licence number",
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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.LightGray,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Back",
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        enabled = specialization != null && expYearsValid && licenceValid,
                        onClick = {
                            viewModel.updateUserProfile {
                                it.copy(
                                    specialization = specialization,
                                    experienceYears = expYears,
                                    licenseNumber = licence
                                )
                            }

                            viewModel.setUserProfile()

                            navController.navigate("home") {
                                popUpTo(0) {
                                    inclusive = true
                                } // Clears everything in backstack
                                launchSingleTop = true // Prevents multiple instances of "home"
                            }
                        },
                        modifier = Modifier,
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black,
                            containerColor = Color.LightGray,
                            disabledContentColor = Color.Black.copy(alpha = 0.6f),
                            disabledContainerColor = Color.LightGray.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            "Save Profile",
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    }
                }
                // Footer
                Spacer(modifier = Modifier.height(60.dp))
            }

        }
    }
}

fun isValidExperience(experience: Int) : Boolean {
    return experience > 0 && experience <= 60
}

fun isValidLicenceNumber(licence: String) : Boolean {
    val trimmed = licence.trim()

    val patterns = listOf(
        // India (MCI, DMC, MMC, KMC, TNMC, UP, RMC)
        Regex("""^(MCI|DMC|MMC|KMC|TNMC|UP|RMC)[/\s-]?[A-Z]?/?\d{4,7}(/?\d{2,4})?$""", RegexOption.IGNORE_CASE),

        // USA (NPI – 10 digits)
        Regex("""^\d{10}$"""),

        // UK (GMC number – 7 digits)
        Regex("""^\d{7}$"""),

        // Australia (AHPRA medical – starts with MED + 10 digits)
        Regex("""^MED\d{10}$""", RegexOption.IGNORE_CASE),

        // Canada (generic format: 5 to 8 alphanumerics)
        Regex("""^[A-Z]{0,3}\d{5,8}$""", RegexOption.IGNORE_CASE)
    )

    return patterns.any { it.matches(trimmed) }
}