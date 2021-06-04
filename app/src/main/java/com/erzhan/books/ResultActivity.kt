package com.erzhan.books

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erzhan.books.MainActivity.Companion.SEARCH_WORD_KEY
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {

    private val BOOK_REQUEST_URL =
        "https://www.googleapis.com/books/v1/volumes?"
    private val QUERY_PARAMETER = "q"
    private val MAX_RES_PARAMETER= "maxResults"

    lateinit var recyclerViewAdapter: BookRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        recyclerViewAdapter = BookRecyclerViewAdapter(ArrayList())
        recyclerView = findViewById(R.id.recyclerViewId)
        resultTextView = findViewById(R.id.resultTextViewId)
        if (isNetworkAvailable(this)) {

            val intent = intent;
            val searchWord = intent.extras?.getString(SEARCH_WORD_KEY)
            resultTextView.text = String.format(Locale.getDefault(), getString(R.string.search_result_format_res, searchWord))

            val bookTask = BookAsyncTask()

            val url = Uri.parse(BOOK_REQUEST_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAMETER, searchWord)
                .appendQueryParameter(MAX_RES_PARAMETER, "10")
                .build()
                .toString()

            bookTask.execute(url)
        }
    }

    private fun updateUI(){
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
    }

    private inner class BookAsyncTask : AsyncTask<String, Void, ArrayList<Book>>() {
        override fun doInBackground(vararg urls: String?): ArrayList<Book>? {
            val count = urls.size
            if (count < 1 || urls[0] == null) return null

            val books: ArrayList<Book>? = urls[0]?.let { QueryUtils.fetchBookData(it) }

            return books
        }

        override fun onPostExecute(result: ArrayList<Book>?) {
            recyclerViewAdapter.books.clear()

            if (result != null && result.isNotEmpty()) {
                recyclerViewAdapter.books.addAll(result)
                updateUI()
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}