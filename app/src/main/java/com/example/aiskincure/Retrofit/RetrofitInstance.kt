package com.example.aiskincure.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL = "https://ai-skincure-model.onrender.com"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL) // Use this for Android emulator to access localhost
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)