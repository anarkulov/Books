package com.erzhan.books

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_WORD_KEY = "SEARCH KEY"
    }

    lateinit var bookNameEditText: EditText
    lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookNameEditText = findViewById(R.id.bookNameEditTextId)
        searchButton = findViewById(R.id.searchButtonId)
        searchButton.setOnClickListener {
            val bookName: String = bookNameEditText.text.toString().trim()

            if (bookName.isNotEmpty()) {

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(SEARCH_WORD_KEY, bookName)
                startActivity(intent)

            } else {
                Toast.makeText(baseContext, "Enter a book name for searching", Toast.LENGTH_SHORT).show()
            }
        }
    }
}