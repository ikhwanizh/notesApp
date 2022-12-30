package com.ferdyfermadi.storyapp.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.ferdyfermadi.storyapp.data.response.LoginResult
import com.ferdyfermadi.storyapp.repository.Repository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    fun setLogin(email: String, password: String) = repository.login(email, password)

    fun getLoginResponse(): LiveData<LoginResult> = repository.getLoginResponse()
}