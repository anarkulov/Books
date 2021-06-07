package com.erzhan.books

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("volumes")
    fun getAllBooks(@Query("q") query: String): Call<Book>
}