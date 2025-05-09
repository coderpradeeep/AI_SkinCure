package UI.Scanpage

import Database.ViewModel
import UI.TopBar
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraExecutor
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aiskincure.R
import com.example.aiskincure.ui.theme.BG
import com.example.aiskincure.ui.theme.DarkBG

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

    LaunchedEffect(Unit) {
        viewModel.isHomePage = false
        viewModel.isScanpage = true
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
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(1.dp, color = Color.LightGray),
                                modifier = Modifier
                                    .size(width = 50.dp, height = 70.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_launcher_background),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            // switch camera button
                            IconButton(
                                onClick = {
                                    takePhoto(
                                        controller = controller,
                                        context = context
                                    ) {

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
                            // capture button
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
            }
        }
    )
}