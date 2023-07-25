package com.example.appsessions.Back

import com.google.gson.annotations.SerializedName
data class LoginResponse(
    @SerializedName("name") val name: String,
    @SerializedName("token") val token: String,
    @SerializedName("email") val email: String,
    @SerializedName("studies") val studies: String
)
