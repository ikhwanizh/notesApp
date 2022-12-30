package com.ferdyfermadi.storyapp.data.response

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)