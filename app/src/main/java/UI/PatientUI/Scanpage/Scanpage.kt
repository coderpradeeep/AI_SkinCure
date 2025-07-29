package UI.PatientUI.Scanpage

import Database.ViewModel
import UI.Common.TopBar
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aiskincure.R
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG
import com.example.aiskincure.ui.theme.Darkcontainer
import com.example.aiskincure.ui.theme.Lightcontainer
import com.example.aiskincure.ui.theme.Orange
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Scanpage(
    viewModel: ViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                LifecycleCameraController.IMAGE_CAPTURE
            )
        }
    }

    val scope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(true)
    var isOpen = rememberSaveable {
        mutableStateOf(false)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
                uri ->
            uri?.let {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                val decodedBitmap = ImageDecoder.decodeBitmap(source)
                viewModel.updateBitmap(decodedBitmap)
                // Do analysis or whatever you want
                // viewModel.analyzeImage(bitmap.value!!)

                isOpen.value = true
                // Now navigate after image is picked
                //navController.navigate("resultpage")
            }
        }
    )

    var bitmap = viewModel.bitmap.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.isScanpage = true
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.isScanpage = false
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

            if (viewModel.permissionGranted) {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Color.Transparent)
                        ) {
                            // Pick local image
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(1.dp, color = Color.LightGray),
                                modifier = Modifier
                                    .size(width = 50.dp, height = 70.dp)
                                    .clickable() {
                                        // TODO: Pick local image
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_launcher_background),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            // capture button
                            IconButton(
                                onClick = {
                                    viewModel.viewModelScope.launch {
                                        takePhoto(
                                            controller = controller,
                                            context = context,
                                            onPhotoTaken = viewModel::updateBitmap
                                        )

                                        isOpen.value = true
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Camera,
                                    contentDescription = "Capture Image",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .scale(2f)
                                )
                            }

                            // switch camera button
                            IconButton(
                                onClick = {
                                    controller.cameraSelector = if(controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else {
                                        CameraSelector.DEFAULT_BACK_CAMERA
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Cameraswitch,
                                    contentDescription = "Switch Camera",
                                    tint = Color.White
                                )
                            }

                        }

                        // Footer
                        Spacer(Modifier.height(60.dp))
                    }
                }

                // Bottom sheet
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // AddBottomSheet
                    if(isOpen.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                isOpen.value = false
                            },
                            sheetState = modalState,
                            containerColor = if(isSystemInDarkTheme()) Darkcontainer else Lightcontainer
                        ) {
                            Column {
                                // Image
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if(isSystemInDarkTheme()) Color.DarkGray
                                        else Color.LightGray
                                    ),
                                    modifier = Modifier
                                        .padding(PaddingValues(horizontal = 16.dp))
                                ) {
                                    bitmap.value?.let {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "Image preview",
                                        )
                                    }
                                }
                                // Scan button
                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Orange,
                                        contentColor = Color.White
                                    ),
                                    onClick = {
                                        scope.launch {
                                            isOpen.value = false

                                            bitmap.value?.let {
                                                viewModel.analyzeImage(it)
                                            }
                                            navController.navigate("resultpage")
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp))
                                        .wrapContentSize()
                                ) {
                                    Text(
                                        text = "Scan",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                    )
                                }

                                // Footer
                                Spacer(Modifier.height(40.dp))
                            }
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
                        color = Orange,
                        strokeWidth = 5.dp,
                    )
                }
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        }
    )
}

fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Bitmap) -> Unit
) {
    //val context = LocalContext.current
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                // Convert ImageProxy to Bitmap
                val bitmap = image.toBitmap() // You would need to implement this conversion
                onPhotoTaken(bitmap)
                image.close() // Don't forget to close the image proxy
            }
            override fun onError(exception: ImageCaptureException) {
                // Handle the error
                Toast.makeText(context, "Error taking photo", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

// Extention function
fun ImageProxy.toBitmap(): Bitmap {
    val yBuffer: ByteBuffer = planes[0].buffer
    val uBuffer: ByteBuffer = planes[1].buffer
    val vBuffer: ByteBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)
    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
    val imageBytes = out.toByteArray()

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}