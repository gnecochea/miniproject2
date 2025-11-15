package com.example.mini_project_2.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// configuring the connection
// Singleton: only ONE instance exists in entire app
object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // lazy initiation means it is created only when first used
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // tells Retrofit to use GSON to convert JSON to Kotlin objects
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
