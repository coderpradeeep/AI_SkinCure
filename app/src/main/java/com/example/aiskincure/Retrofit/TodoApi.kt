package com.example.aiskincure.Retrofit

import androidx.camera.core.ImageProcessor.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict/")
    suspend fun predictImage(
        @Part file: MultipartBody.Part
    ): String // or any other response type returned by your FastAPI app
}
