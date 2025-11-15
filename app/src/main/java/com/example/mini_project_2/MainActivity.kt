package com.example.mini_project_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mini_project_2.ui.UserListScreen
import com.example.mini_project_2.ui.theme.Miniproject2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Miniproject2Theme {
                UserListScreen()
            }
        }
    }
}
