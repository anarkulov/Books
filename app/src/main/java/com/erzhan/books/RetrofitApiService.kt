package com.erzhan.books

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("volumes")
    fun getAllBooks(@Query("q") query: String): Call<Book>

// Call<ArrayList<Book>> throws an error -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT
// Call<Book> returns only one book, not the list of book
}