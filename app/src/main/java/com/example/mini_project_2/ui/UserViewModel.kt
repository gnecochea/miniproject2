package com.example.mini_project_2.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project_2.data.local.UserDatabase
import com.example.mini_project_2.data.remote.RetrofitInstance
import com.example.mini_project_2.data.repository.UserRepository
import com.example.mini_project_2.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// define business logic functions
class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UserDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(RetrofitInstance.api, dao)

    val users: StateFlow<List<User>> =
        repository.users.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // filtered users changes when query changes
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredUsers: StateFlow<List<User>> =
        searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                if (query.isBlank()) repository.users
                else repository.searchUsers(query)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun updateQuery(query: String) {
        searchQuery.value = query
    }

    // we chose to refresh users asynchronously for UI to stay responsive
    // it will run on the background IO thread bc it is an API call
    // we do not want to run on UI/Main thread bc we are not updating the UI
    // we do not want to run on Default thread bc this is not CPU intensive
    fun refreshUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshUsers()
        }
    }
}