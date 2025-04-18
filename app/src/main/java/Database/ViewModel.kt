package Database

import Database.DataType.ChatDataType
import com.example.aiskincure.Room.RoomApplication
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    val todoDao = RoomApplication.todoDatabase.getTodoDao()

    var isUser by mutableStateOf(false)
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
    var firstChat by mutableStateOf(true)
    var prompt by mutableStateOf("")

    fun chatAI(model: GenerativeModel) {
        viewModelScope.launch {
            try {
                val response = model.generateContent(
                    content {
                        text("$prompt ? Give brief within 50 words")
                    }
                )

                addChat(response.text.toString())
                Log.i("Success", response.text.toString())
            }
            catch (e : Error) {
                Log.i("Error", "Error")
            }
        }

        firstChat = false
    }

    var isChatPage by mutableStateOf(false)
}