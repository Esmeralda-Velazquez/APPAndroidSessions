package com.example.appsessions.Back

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.example.appsessions.Models.Session

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest:LoginRequest):Call<LoginResponse>

    @GET("sessions")
    fun getAllSessions(): Call<List<Session>>

}

