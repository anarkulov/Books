package com.erzhan.books

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erzhan.books.MainActivity.Companion.SEARCH_WORD_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {

    lateinit var bookAdapter: BookRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var resultTextView: TextView

    lateinit var books: ArrayList<Book>

    // retrofit
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitClientInstance: RetrofitClientInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bookAdapter = BookRecyclerViewAdapter(ArrayList())
        recyclerView = findViewById(R.id.recyclerViewId)
        resultTextView = findViewById(R.id.resultTextViewId)
        books = ArrayList()
        retrofitClientInstance = RetrofitClientInstance()
        retrofit = retrofitClientInstance.getRetrofitInstance()

        val intent = intent;
        val searchWord = intent.extras?.getString(SEARCH_WORD_KEY)
        resultTextView.text = String.format(
            Locale.getDefault(),
            getString(R.string.search_result_format_res, searchWord)
        )

        loadData(searchWord)
    }

    private fun loadData(searchWord: String?) {

        val booksApi = retrofit.create(RetrofitApiService::class.java)
        val response = booksApi.getAllBooks(searchWord!!)

        response.enqueue(object : Callback<Book> {
            override fun onResponse(
                call: Call<Book>,
                response: Response<Book>
            ) {
                if (response.isSuccessful) {
                    val bodyResponse = response.body()
                    if (bodyResponse != null) {
                         books.addAll(listOf(bodyResponse))
                    }
                    bookAdapter.books = books
                    recyclerView.adapter = bookAdapter
                    recyclerView.layoutManager = LinearLayoutManager(baseContext)

                    Toast.makeText(baseContext, "Successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext, "Error: isNot successful", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Toast.makeText(baseContext, "Error: onFailure ", Toast.LENGTH_SHORT).show()
                Log.v("Result Activity", t.toString())
            }
        })

    }
}