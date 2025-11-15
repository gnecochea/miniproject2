package com.example.mini_project_2.data.remote

import com.example.mini_project_2.domain.model.User
import retrofit2.http.GET

// describes the endpoints
interface ApiService {
    // "users" is the endpoint path added to base URL and GET is the HTTP method
    @GET("users")
    // List<User> defines the return type of the response
    suspend fun getUsers(): List<User>
}
