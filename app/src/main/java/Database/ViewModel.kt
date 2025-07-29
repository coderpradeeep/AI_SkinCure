package Database

import Database.DataType.ChatDataType
import Database.DataType.UserDataType
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aiskincure.Retrofit.apiService
import com.example.aiskincure.Retrofit.bitmapToMultipart
import com.example.aiskincure.Room.RoomApplication
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class ViewModel : ViewModel() {

    private val todoDao = RoomApplication.todoDatabase.getTodoDao()

// ChatBot workings
    var isUser by mutableStateOf(true) // for chatBot page
    val chatList = todoDao.getAllChat()

    fun addChat(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                todoDao.insertChat(ChatDataType(isUser = isUser, text = text))
                // Switch user type on main thread to update UI correctly
                withContext(Dispatchers.Main) {
                    isUser = !isUser
                }

                Log.d("ChatInsertSuccess", "Chat inserted: $text")
            } catch (e: Exception) {
                Log.e("ChatInsertError", "Failed to insert chat: ${e.message}", e)
            }
        }
    }

    fun deleteChat() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                todoDao.deleteMyChatList()
                Log.d("ChatDeleteSuccess", "Chat list deleted")
            } catch (e: Exception) {
                Log.e("ChatDeleteError", "Failed to delete chat: ${e.message}", e)
            }
        }
    }

    var enteredText by mutableStateOf("")
    var prompt by mutableStateOf("")

    private var chatSession: Chat? = null

    fun startChat(model: GenerativeModel) {
        try {
            if (chatSession == null) {
                chatSession = model.startChat()

                Log.d("ChatStartSuccess", "Chat started")
            }
        }
        catch (e: Exception) {
            Log.e("ChatStartError", "Failed to start chat: ${e.message}", e)
        }
    }

    fun chatAI(prompt: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = chatSession?.sendMessage(prompt)
                val text = response?.text
                if (!text.isNullOrBlank()) {
                    addChat(text)
                    Log.i("ChatSuccess", "Response: $text")
                } else {
                    Log.w("ChatWarning", "Response was null or empty")
                }
            } catch (e: Exception) {
                Log.e("ChatError", "Chat AI call failed: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }

// Scanning and resulting
    var isChatPage by mutableStateOf(false)
    var isHomePage by mutableStateOf(false)
    var isConsultantPage by mutableStateOf(false)
    var isHistoryPage by mutableStateOf(false)
    var isScanpage by mutableStateOf(false)
    var permissionGranted by mutableStateOf(false)

    var imageUri by mutableStateOf<Uri?>(null)

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    fun updateBitmap(bitmap: Bitmap?) {
        _bitmap.value = bitmap
    }

    var isLoading by mutableStateOf(false)

    private val _response = MutableStateFlow<String?>(null)
    val response = _response.asStateFlow()

    private var analyzeJob: Job? = null

    fun cancelAnalysis() {
        analyzeJob?.cancel()
        isLoading = false
    }

    fun analyzeImage(bitmap: Bitmap?) {
        if (bitmap == null) {
            Log.e("AnalyzeImage", "Bitmap is null, skipping analysis.")
            return
        }
        analyzeJob?.cancel()

        viewModelScope.launch {
            isLoading = true

            try {
                val filePart = bitmapToMultipart(bitmap)
                val response = apiService.predictImage(filePart)
                _response.value = response.toString()
                Log.i("ImageAnalysisSuccess", "Prediction: $response")
            } catch (e: CancellationException) {
                Log.i("AnalyzeImage", "Analysis cancelled")
            } catch (e: Exception) {
                Log.e("AnalyzeImage", "API call failed: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }

// Login and User data
    private val _userProfileInfo = MutableStateFlow<UserDataType?>(null)
    val userProfileInfo: StateFlow<UserDataType?> = _userProfileInfo.asStateFlow()

    fun setGuestMode() {
        try {
            _userProfileInfo.value = UserDataType().copy(isGuest = true)
            Log.d("GuestMode", "Guest mode set: ${_userProfileInfo.value?.isGuest}")
        }
        catch (e: Exception) {
            Log.e("GuestModeError", "Failed to set guest mode: ${e.message}", e)
        }
    }

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val profile = todoDao.getUserProfile()
                _userProfileInfo.value = profile
                Log.d("userProfileSet", "Fetched User Profile: $_userProfileInfo")
            } catch (e: Exception) {
                Log.e("userProfileGetError", "Failed to fetch user Profile: ${e.message}", e)
            }
        }
    }

    fun updateUserProfile(modifier: (UserDataType) -> UserDataType) {
        try {
            val current = _userProfileInfo.value ?: UserDataType.guest() // or your default
            val updated = modifier(current)
            _userProfileInfo.value = updated
            //setUserProfile()

            Log.d("updateUserProfileSuccess", "User profile updated: $updated")
        } catch (e: Exception) {
            Log.e("updateUserProfileError", "Exception during user profile update: ${e.message}", e)
        }
    }

    fun setUserProfile() {
        val user = _userProfileInfo.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                todoDao.setUserProfile(user ?: return@launch)
                _userProfileInfo.value = user
                Log.d("userProfileSetSuccess", "User profile set: $user")
            } catch (e: Exception) {
                Log.e("userProfileSetError", "Failed to set user Profile: ${e.message}", e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wasPatient = _userProfileInfo.value?.isPatient == true  // retain role before logout

                // Step 1: Clear from DB
                todoDao.clearUserProfile()

                // Step 2: Set guest profile on Main thread
                withContext(Dispatchers.Main) {
                    _userProfileInfo.value = UserDataType.guest().copy(isPatient = wasPatient)
                }

                Log.d("logoutSuccess", "User successfully logged out. Guest profile set.")
            } catch (e: Exception) {
                Log.e("logoutError", "Failed to log out: ${e.message}", e)
            }
        }
    }

    private val _isAppReady = MutableStateFlow(false)
    val isAppReady: StateFlow<Boolean> = _isAppReady

    suspend fun preloadAppData() = withContext(Dispatchers.IO) {
        try {
            // Load user profile
            getUserProfile()
            Log.d("PreloadSuccess", "Initial data loaded")

            _isAppReady.value = true
            Log.d("PreloadSuccess", "App is ready")
        } catch (e: Exception) {
            Log.e("PreloadFailure", "Failed during preload: ${e.message}", e)
        }
    }

}
