//package com.erzhan.books
//
//import android.text.TextUtils
//import android.util.Log
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.lang.Integer.parseInt
//import java.net.HttpURLConnection
//import java.net.MalformedURLException
//import java.net.URL
//import java.nio.charset.StandardCharsets
//import kotlin.collections.ArrayList
//
//class QueryUtils {
//
//    companion object {
//
//        fun fetchBookData(requestUrl: String): ArrayList<Book>? {
//            val url: URL? = createUrl(requestUrl)
//
//            var jsonResponse: String? = null
//            try {
//                jsonResponse = makeHttpRequest(url)
//            } catch (e: IOException){
//                Log.v("QueryUtils", "Error closing input stream", e)
//            }
//
//            val books : ArrayList<Book>? = jsonResponse?.let { extractBooks(it) }
//
//            return books
//        }
//
//        private fun createUrl(stringURL: String): URL? {
//            var url: URL? = null
//
//            try {
//                url = URL(stringURL)
//            } catch (e: MalformedURLException){
//                Log.v("QueryUtils", "Error closing input", e)
//            }
//
//            return url
//        }
//
//        private fun makeHttpRequest(url: URL?): String {
//            var jsonResponse = ""
//
//            if (url == null){
//                return jsonResponse
//            }
//
//            var urlConnection: HttpURLConnection? = null
//            var inputStream: InputStream? = null;
//            try {
//                urlConnection = url.openConnection() as HttpURLConnection?
//                urlConnection!!.readTimeout = 10000
//                urlConnection.connectTimeout = 15000
//                urlConnection.requestMethod = "GET"
//                urlConnection.connect()
//
//                if (urlConnection.responseCode == 200){
//                    inputStream = urlConnection.inputStream
//                    jsonResponse = readFromStream(inputStream)
//                } else {
//                    Log.e("QueryUtils", "Error response code: " + urlConnection.responseCode)
//                }
//            } catch (e: IOException){
//                Log.v("QueryUtil", "Problem retrieving the earthquake JSON results.", e)
//            } finally {
//                urlConnection?.disconnect()
//                inputStream?.close()
//            }
//
//            return jsonResponse
//        }
//
//        private fun readFromStream(inputStream: InputStream?): String {
//            val output = StringBuilder()
//            if (inputStream != null) {
//                val inputStreamReader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
//                val reader = BufferedReader(inputStreamReader)
//                var line = reader.readLine()
//                while (line != null) {
//                    output.append(line)
//                    line = reader.readLine()
//                }
//            }
//            return output.toString()
//        }
//
//        private fun extractBooks(booksJSON: String): ArrayList<Book>? {
//            if (TextUtils.isEmpty(booksJSON)) {
//                return null
//            }
//
//            val books: ArrayList<Book> = ArrayList()
//
//            try {
//                val jsonObject = JSONObject(booksJSON)
//                val jsonArray = jsonObject.getJSONArray("items")
//
//                for (i in 0 until jsonArray.length()){
//                    Log.v("Queryy", i.toString())
//                    val currentBook = jsonArray.getJSONObject(i)
//                    val bookInfoObject = currentBook.getJSONObject("volumeInfo")
//
//                    val title = bookInfoObject.getString("title")
//                    val authors = bookInfoObject.getJSONArray("authors")
//                    val genre : String = if (bookInfoObject.has("categories")){
//                        bookInfoObject.getJSONArray("categories")[0].toString()
//                    } else {
//                        "Not available"
//                    }
//                    val date : String = if (bookInfoObject.has("publishedDate")){
//                        bookInfoObject.getString("publishedDate")
//                    } else {
//                        "Not available"
//                    }
//                    val rating: Double = if (bookInfoObject.has("averageRating")){
//                        bookInfoObject.getString("averageRating").toDouble()
//                    } else {
//                        0.0;
//                    }
//                    val numberOfPages : Int = if (bookInfoObject.has("pageCount")){
//                        parseInt(bookInfoObject.getString("pageCount"))
//                    } else {
//                        0
//                    }
//
//                    val imageLink = bookInfoObject.getJSONObject("imageLinks")
//                    val infoLink = bookInfoObject.getString("infoLink")
//
////                    val book = Book(title, authors[0] as String, genre, date, imageLink.getString("thumbnail"), numberOfPages, rating, infoLink)
//
////                    books.add(book)
//                }
//
//            } catch (e: JSONException){
//                Log.v("QueryUtils", "Problem parsing the book JSON results", e)
//            }
//
//            return books
//        }
//    }
//}