package com.example.firebaseauth.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebaseauth.data.Response
import com.example.firebaseauth.data.model.Post
import com.example.firebaseauth.data.repository.AuthRepository
import com.example.firebaseauth.data.repository.PostRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _response: MutableStateFlow<Response<List<Post>>> =
        MutableStateFlow(Response.Idle)
    val response: StateFlow<Response<List<Post>>> = _response

    private val _authState: MutableStateFlow<Response<FirebaseUser?>> =
        MutableStateFlow(Response.Idle)
    val authState: StateFlow<Response<FirebaseUser?>> = _authState

    init {
        getCurrentUser()
        getAllHeroes()
    }

    private fun getCurrentUser() {
        val user = authRepository.currentUser
        _authState.value =
            if (user != null) Response.Success(user) else Response.Error("User not logged in")
    }

    private fun getAllHeroes() {
        viewModelScope.launch {
            postRepository.getPosts().collect { response ->
                _response.value = response
            }
        }

    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authRepository = AuthRepository(context)
                val postRepository = PostRepository()
                HomeViewModel(
                    authRepository = authRepository,
                    postRepository = postRepository
                )
            }
        }
    }
}
