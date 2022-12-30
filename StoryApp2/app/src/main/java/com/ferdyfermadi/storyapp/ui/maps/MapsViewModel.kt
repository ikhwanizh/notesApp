package com.ferdyfermadi.storyapp.ui.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ferdyfermadi.storyapp.data.response.ListStoryItem
import com.ferdyfermadi.storyapp.repository.Repository

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    fun getStoriesMap(token: String) {
        repository.getStoriesMap(token)
    }

    fun getStoriesMapResponse(): LiveData<ArrayList<ListStoryItem>> = repository.getStoriesMapResponse()
}