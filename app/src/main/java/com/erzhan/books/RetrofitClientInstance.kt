package com.erzhan.books

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    companion object {
        private val BASE_URL = "https://www.googleapis.com/books/v1/"
    }

    private var retrofit: Retrofit? = null

    fun getRetrofitInstance() : Retrofit {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}