package com.example.mini_project_2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserListScreen(
    viewModel: UserViewModel = viewModel()
) {
    var search by remember { mutableStateOf("") }
    // Not displaying API data directly to UI
    // collecting StateFlow with collectAsState()
    val users by viewModel.filteredUsers.collectAsState()

    Scaffold(
        // Search bar at the top
        topBar = {
            TextField(
                value = search,
                onValueChange = {
                    search = it
                    viewModel.updateQuery(it)
                },
                label = { Text("Search by name or email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .windowInsetsPadding(WindowInsets.statusBars) // ⬅️ FIX
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Display: id, name, email, and phone number
                    Column(Modifier.padding(16.dp)) {
                        Text(text = user.id.toString())
                        Text(text = user.name, fontWeight = FontWeight.Bold)
                        Text(text = user.email)
                        Text(text = user.phone)

                    }
                }
            }
        }
    }

    // fetch data on first launch
    // otherwise, API refresh would run on every recomposition
    LaunchedEffect(Unit) {
        viewModel.refreshUsers()
    }
}
