package com.example.search.model

import com.google.gson.annotations.SerializedName


data class BaseModel(


    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("tokenType") val tokenType: String

    )