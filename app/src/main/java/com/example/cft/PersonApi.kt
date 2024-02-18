package com.example.cft

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonApi {

    @GET("api/")
    fun getPerson(
        @Query("results") results: Int
    ): Call<PersonResponse>
}