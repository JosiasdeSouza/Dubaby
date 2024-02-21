package com.example.dubaby.models

import retrofit2.http.GET

interface FirebaseAPI {
    @GET("usercategories.json")
    suspend fun getCategories(): List<UserCategory>
}
