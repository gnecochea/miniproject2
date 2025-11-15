package com.example.mini_project_2.data.repository

import com.example.mini_project_2.domain.model.User
import android.util.Log
import com.example.mini_project_2.data.local.UserDao
import com.example.mini_project_2.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

// middleman between ViewModel and DAO + API
// here we implement offline-first
class UserRepository(
    private val api: ApiService,
    private val dao: UserDao
) {
    // passing the Dao's flow
    val users: Flow<List<User>> = dao.getAllUsers()

    // Fetch users from API and update the local Room database.
    // If the API call fails, keep showing cached data.
    // InsertAll from UserDao was a suspend function so it can only
    // be called from another suspend function
    suspend fun refreshUsers() {
        try {
            // api call getUsers returns a list of user objects from JSON
            val response = api.getUsers()
            // insert all receives a list of user objects
            dao.insertAll(response)
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to fetch users: ${e.message}")
        }
    }

    fun searchUsers(query: String): Flow<List<User>> = dao.searchUsers(query)
}
