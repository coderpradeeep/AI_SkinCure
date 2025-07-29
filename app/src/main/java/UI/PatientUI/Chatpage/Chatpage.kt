package UI.PatientUI.Chatpage

import Database.ViewModel
import UI.Common.TopBar
import UI.PatientUI.ConsultantPage.Consultantpage
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.aiskincure.BuildConfig
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkcontainer
import com.example.aiskincure.ui.theme.Darkpink
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Lightpink
import com.example.aiskincure.ui.theme.Pink
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

lateinit var generativeModel : GenerativeModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Chatpage(
    viewModel: ViewModel,
    navController: NavHostController,
) {
    val scope = rememberCoroutineScope()

    val chatList by viewModel.chatList.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.isChatPage = true

        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.APIKEY
        )
        viewModel.startChat(generativeModel)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isChatPage = false
        }
    }

    Scaffold(
        topBar = {
            TopBar( viewModel, navController)
        }
    ) {
        Surface(
            color = if (isSystemInDarkTheme()) DarkBG else BG,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Chats
            if (chatList == null) {
                Text(
                    text = "Feel free to ask anything with your AI assistant",
                    color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 0.sp,
                    lineHeight = 30.sp,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(PaddingValues(24.dp))
                )
            } else {
                chatList?.let { chat ->
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom,
                        state = LazyListState(
                            firstVisibleItemIndex = chat.size,
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .animateContentSize()
                            .padding(horizontal = 16.dp)
                            .navigationBarsPadding()
                            .statusBarsPadding()
                            .animateContentSize()
                    ) {
                        // Spacer for TopappBar
                        item {
                            Spacer(Modifier.height(80.dp))
                        }

                        // Demo chat
                        items(chat, key = { it.id }) { item ->
                            if (item.isUser) {
                                ChatUserShape(item.text)
                            } else {
                                ChatAIShape(item.text)
                            }
                        }

                        // Spacer, Not padding bcoz it does not block the view
                        item {
                            Spacer(Modifier.height(100.dp))
                        }

                    }
                }
            }

            // TextField and Search Button on top of chats
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(PaddingValues(bottom = 4.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Spacer(
                        Modifier
                            .weight(1f)
                            .background(Color.Transparent)
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = if (isSystemInDarkTheme()) listOf(
                                        Color.Transparent,
                                        DarkBG
                                    )
                                    else listOf(Color.Transparent, BG),
                                    startY = 0f,
                                    endY = 70f
                                )
                            )
                            .padding(PaddingValues(vertical = 16.dp, horizontal = 16.dp))
                    ) {
                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(
                                cursorColor = Pink,
                                focusedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                unfocusedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                disabledTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedBorderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                disabledBorderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                unfocusedBorderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                unfocusedPlaceholderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                focusedPlaceholderColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                disabledContainerColor = Color.Transparent,
                                focusedLabelColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                unfocusedLabelColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                disabledLabelColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                            ),
                            enabled = viewModel.isUser,
                            shape = CircleShape,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    tint = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                )
                            },
                            label = {
                                Text(
                                    text = "Ask a Question",
                                    color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                                )
                            },
                            value = viewModel.enteredText,
                            onValueChange = {
                                viewModel.enteredText = it
                            },
                            maxLines = 2,
                            textStyle = TextStyle(color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = null
                            ),
                            modifier = Modifier
                                .background(Color.Transparent)
                                .weight(1f)
                        )

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Pink,
                                contentColor = Color.White,
                                disabledContainerColor = Pink,
                                disabledContentColor = Color.White
                            ),
                            shape = CircleShape,
                            enabled = viewModel.enteredText != "",
                            onClick = {
                                viewModel.viewModelScope.launch {
                                    viewModel.addChat(viewModel.enteredText)
                                    viewModel.prompt = viewModel.enteredText
                                    viewModel.chatAI(viewModel.prompt)
                                    viewModel.enteredText = ""
                                }
                            },
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .width(IntrinsicSize.Min)
                                .padding(PaddingValues(start = 16.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Send,
                                contentDescription = null,
                                //tint = if(isSystemInDarkTheme()) Color.LightGray else Color.White,
                                modifier = Modifier
                                    .scale(1.2f)
                                    .padding(PaddingValues(10.dp))
                            )
                        }

                    }
                }
            }

            if(viewModel.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(if(isSystemInDarkTheme()) Color.Black.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.5f))
                ) {
                    CircularProgressIndicator(
                        color = Pink,
                        strokeWidth = 5.dp,
                        trackColor = Color.Transparent,
                        modifier = Modifier
                            .scale(0.7f)
                    )
                }
            }

        }
    }
}

@Composable
private fun ChatUserShape(
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Right,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .animateContentSize()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if(isSystemInDarkTheme()) Darkpink else Lightpink,
                contentColor = if(isSystemInDarkTheme()) Color.LightGray else Color.Black
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(start = 60.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .background(Color.Transparent)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 12.dp, vertical = 6.dp))
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
private fun ChatAIShape(
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if(isSystemInDarkTheme()) Darkcontainer else Lightcontainer,
                contentColor = if(isSystemInDarkTheme()) Color.LightGray else Color.Black
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(end = 60.dp, top = 6.dp, bottom = 6.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .background(Color.Transparent)
        ) {
            Text(
                text = text,
                //color = if(isSystemInDarkTheme()) Color.LightGray else Color.White,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 12.dp, vertical = 6.dp))
                    .align(Alignment.Start)
            )
        }
    }
}