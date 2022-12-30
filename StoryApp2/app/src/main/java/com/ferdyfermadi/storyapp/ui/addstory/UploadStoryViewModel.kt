package com.ferdyfermadi.storyapp.ui.addstory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ferdyfermadi.storyapp.data.response.DataResponse
import com.ferdyfermadi.storyapp.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadStoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    fun addStory(token: String, imageMultipart: MultipartBody.Part, description: RequestBody) {
        repository.addStory(token, imageMultipart, description)
    }

    fun getStoryResponse(): LiveData<DataResponse> = repository.getDataResponse()
}