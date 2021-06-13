package com.erzhan.books

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erzhan.books.MainActivity.Companion.SEARCH_WORD_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.security.acl.Group
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity(), BookRecyclerViewAdapter.OnItemClickListener {

    lateinit var bookAdapter: BookRecyclerViewAdapter
    lateinit var bookList: ArrayList<VolumeInfo>

    private lateinit var recyclerView: RecyclerView
    private lateinit var resultTextView: TextView
    private lateinit var noResultTextView: TextView
    private lateinit var loadingProgressBar: ProgressBar

    // retrofit
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitClientInstance: RetrofitClientInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bookAdapter = BookRecyclerViewAdapter(ArrayList(), this)
        bookList = ArrayList()

        recyclerView = findViewById(R.id.recyclerViewId)
        resultTextView = findViewById(R.id.resultTextViewId)
        noResultTextView = findViewById(R.id.noResultTextViewId)
        loadingProgressBar = findViewById(R.id.loadingProgressBarId)

        retrofitClientInstance = RetrofitClientInstance()
        retrofit = retrofitClientInstance.getRetrofitInstance()

//    Receive intent from MainActivity

        val intent = intent;
        val searchWord = intent.extras?.getString(SEARCH_WORD_KEY)
        resultTextView.text = String.format(
            Locale.getDefault(),
            getString(R.string.search_result_format_res, searchWord)
        )
        loadData(searchWord)
    }

    private fun loadData(searchWord: String?) {
        setVisibility(false)

        val booksApi = retrofit.create(RetrofitApiService::class.java)
        val response = booksApi.getAllBooks(searchWord!!)

        response.enqueue(object : Callback<Book> {
            override fun onResponse(
                call: Call<Book>,
                response: Response<Book>
            ) {
                if (response.isSuccessful) {
                    try {
                        val bodyResponse = response.body()
                        if (bodyResponse != null) {
                            bookList.addAll(bodyResponse.items)
                            bookAdapter.books = bookList
                            recyclerView.adapter = bookAdapter
                            recyclerView.layoutManager = LinearLayoutManager(baseContext)
                            setVisibility(true)

                        }
                    } catch (npe: NullPointerException){
                        setVisibility(true)
                    }

                } else {
                    Toast.makeText(baseContext, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Toast.makeText(baseContext, "Error: onFailure ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setVisibility(status: Boolean){

        if (bookAdapter.itemCount == 0){
            noResultTextView.visibility = View.VISIBLE
            resultTextView.visibility = View.GONE
            recyclerView.visibility = View.GONE
            loadingProgressBar.visibility = View.GONE
        } else {
            if (status) {
                loadingProgressBar.visibility = View.GONE
                resultTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            noResultTextView.visibility = View.GONE
        }
    }

    override fun onItemClick(position: Int, data: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(data)
        startActivity(intent)
    }
}