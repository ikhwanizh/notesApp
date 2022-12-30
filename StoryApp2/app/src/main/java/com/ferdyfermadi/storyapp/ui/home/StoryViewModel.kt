package com.ferdyfermadi.storyapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferdyfermadi.storyapp.data.response.DataResponse
import com.ferdyfermadi.storyapp.data.response.ListStoryItem
import com.ferdyfermadi.storyapp.data.response.StoriesResponse
import com.ferdyfermadi.storyapp.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    fun getAllStory(token: String): LiveData<PagingData<ListStoryItem>> =
        repository.getAllStory(token).cachedIn(viewModelScope)
}