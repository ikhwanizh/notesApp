package com.ferdyfermadi.storyapp.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ferdyfermadi.storyapp.data.response.LoginResult
import kotlinx.coroutines.launch

class UserViewModel (private val pref: UserPreference) : ViewModel() {

    fun getUserSession(): LiveData<LoginResult> = pref.getUser().asLiveData()

    fun saveUserSession(name: String, userId: String, token: String) {
        viewModelScope.launch {
            pref.saveUser(name, userId, token)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}