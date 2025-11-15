package com.example.mini_project_2.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mini_project_2.domain.model.User
import kotlinx.coroutines.flow.Flow

// Room part 2: How to save it (functions)
// note all functions
@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    // FLOW: will emit a new list of User objects every time the users table changes
    fun getAllUsers(): Flow<List<User>>

    // search users by name or email
    // FLOW: will emit a new list of User objects every time the users table changes
    @Query("SELECT * FROM users WHERE LOWER(name) LIKE '%' || " +
            "LOWER(:query) || '%' OR LOWER(email) LIKE '%' || LOWER(:query) || '%'")
    fun searchUsers(query: String): Flow<List<User>>


    // REPLACE strategy replaces the existing row
    // with the new one when primary key conflicts occur
    // NOT FLOW: one-time action
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // inserting is slow and doesn't affect the UI,
    // so it needs to be able to run in background thread
    suspend fun insertAll(users: List<User>)
}
