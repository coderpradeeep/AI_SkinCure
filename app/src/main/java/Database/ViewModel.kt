package Database

import Database.DataType.ChatDataType
import android.app.Activity
import android.net.Uri
import com.example.aiskincure.Room.RoomApplication
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    val todoDao = RoomApplication.todoDatabase.getTodoDao()

    var isUser by mutableStateOf(true)
    val chatList = todoDao.getAllChat()

    fun addChat(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertChat(ChatDataType(isUser = isUser, text = text))
            isUser = !isUser
        }
    }

    fun deleteChat(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteMyChatList(id)
        }
    }

    var enteredText by mutableStateOf("")
    //var firstChat by mutableStateOf(true)
    var prompt by mutableStateOf("")

    private var chatSession: Chat? = null

    fun startChat(model: GenerativeModel) {
        if (chatSession == null) {
            chatSession = model.startChat()
        }
    }

    fun chatAI(prompt: String) {
        viewModelScope.launch {
            try {
                val response = chatSession?.sendMessage(prompt)
                response?.text?.let {
                    addChat(it)
                }
                if (response != null) {
                    Log.i("Success", response.text.toString())
                }
            }
            catch (e : Error) {
                Log.i("Error", "Error")
            }
        }

    }

    var isChatPage by mutableStateOf(false)
    var isHomePage by mutableStateOf(true)
    var isScanpage by mutableStateOf(false)

    var permissionGranted by mutableStateOf(false)

    var imageUri by mutableStateOf<Uri?>(null)

    fun analyzeImage() {
        viewModelScope.launch {

        }
    }

}