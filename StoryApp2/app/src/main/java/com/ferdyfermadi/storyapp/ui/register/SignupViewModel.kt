package com.ferdyfermadi.storyapp.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ferdyfermadi.storyapp.data.response.DataResponse
import com.ferdyfermadi.storyapp.repository.Repository

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    fun saveUser(name: String, email: String, password: String) = repository.register(name, email, password)

    fun getRegisterResponse(): LiveData<DataResponse> = repository.getRegisterResponse()
}