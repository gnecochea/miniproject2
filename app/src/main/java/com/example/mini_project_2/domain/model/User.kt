package com.example.mini_project_2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room part 1: what to save (data class)
// database table users
// created based on API JSON
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)
