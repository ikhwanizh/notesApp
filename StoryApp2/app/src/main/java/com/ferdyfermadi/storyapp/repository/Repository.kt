package com.ferdyfermadi.storyapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ferdyfermadi.storyapp.data.api.ApiConfig
import com.ferdyfermadi.storyapp.data.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(application: Application) {
    val registerResponse = MutableLiveData<DataResponse>()
    val loginResponse = MutableLiveData<LoginResult>()
    val addStoryResponse = MutableLiveData<DataResponse>()
    val listStoriesMap = MutableLiveData<ArrayList<ListStoryItem>>()

    fun register(name: String, email: String, password: String) {
        ApiConfig.getApiService().register(name, email, password)
            .enqueue(object : Callback<DataResponse> {
                override fun onFailure(
                    call: Call<DataResponse>,
                    t: Throwable
                ) {
                    Log.e("SignUpViewModel", "onFailure: ${t.message}")
                }
                override fun onResponse(
                    call: Call<DataResponse>,
                  response: Response<DataResponse>
                ) {
                    if (response.isSuccessful) {
                        registerResponse.postValue(response.body())
                    } else {
                        registerResponse.postValue(response.body())
                    }
                }
            })
        }


    fun getRegisterResponse(): LiveData<DataResponse> = registerResponse

    fun login(email: String, password: String) {
        ApiConfig.getApiService().login(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(
                    call: Call<LoginResponse>,
                    t: Throwable
                ) {
                    Log.e("SignInViewModel", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.postValue(response.body()?.loginResult)
                    } else {
                        loginResponse.postValue(response.body()?.loginResult)
                    }
                }
            })
        }

    fun getLoginResponse(): LiveData<LoginResult> = loginResponse

    fun getAllStory(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource("Bearer $token")
            }
        ).liveData
    }

    fun addStory(token: String, imageMultipart: MultipartBody.Part, description: RequestBody) {
        ApiConfig.getApiService().postStory("Bearer $token", imageMultipart, description)
            .enqueue(object : Callback<DataResponse> {
                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Log.e("AddStoryViewModel", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    if (response.isSuccessful) {
                        addStoryResponse.postValue(response.body())
                    } else {
                        addStoryResponse.postValue(response.body())
                    }
                }
            })
        }

    fun getDataResponse(): LiveData<DataResponse> = addStoryResponse

    fun getStoriesMap(token: String) {
        ApiConfig.getApiService().listMaps("Bearer $token")
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        listStoriesMap.postValue(response.body()?.listStory as ArrayList<ListStoryItem>?)
                    } else {
                        listStoriesMap.postValue(response.body()?.listStory as ArrayList<ListStoryItem>?)
                    }
                }

                override fun onFailure(
                    call: Call<StoriesResponse>,
                    t: Throwable
                ) {
                    Log.e("MapViewModel", "onFailure: ${t.message}")
                }
            })
    }

    fun getStoriesMapResponse(): LiveData<ArrayList<ListStoryItem>> = listStoriesMap


}