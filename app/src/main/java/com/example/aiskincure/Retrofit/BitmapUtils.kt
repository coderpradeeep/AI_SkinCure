package com.example.aiskincure.Retrofit

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

fun bitmapToMultipart(bitmap: Bitmap, name: String = "image.png"): MultipartBody.Part {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()
    val requestBody = byteArray.toRequestBody("image/png".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", name, requestBody)
}