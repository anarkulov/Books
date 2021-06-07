package com.erzhan.books

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import java.text.Format
import java.util.*
import kotlin.collections.ArrayList

class BookRecyclerViewAdapter constructor(books: ArrayList<Book>) : RecyclerView.Adapter<BookRecyclerViewAdapter.MyViewHolder>() {

    var books: ArrayList<Book>

    lateinit var context: Context;

    init {
        this.books = books
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitleTextView: TextView
        val bookAuthorTextView: TextView
        val bookGenreTextView: TextView
        val bookDateTextView: TextView
        val bookImageView: ImageView
        val bookPagesTextView: TextView
        val bookRatingBar: RatingBar

        init {
            bookTitleTextView = itemView.findViewById(R.id.bookTitleTextViewId)
            bookAuthorTextView = itemView.findViewById(R.id.bookAuthorTextViewId)
            bookGenreTextView = itemView.findViewById(R.id.bookGenreTextViewId)
            bookDateTextView = itemView.findViewById(R.id.bookDateTextViewId)
            bookImageView = itemView.findViewById(R.id.bookImageViewId)
            bookPagesTextView = itemView.findViewById(R.id.bookPagesTextViewId)
            bookRatingBar = itemView.findViewById(R.id.bookRatingBarId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.book_item, parent, false)

        val viewHolder = MyViewHolder(view);

        return viewHolder;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bookTitleTextView.text = books[position].items.get(position).volumeInfo.title
        holder.bookAuthorTextView.text = String.format(Locale.getDefault(), context.getString(R.string.author_format_res),
            books[position].items[position].volumeInfo.authors[0]
        )
        holder.bookGenreTextView.text = String.format(Locale.getDefault(), context.getString(R.string.genre_format_res), books[position].items[position].volumeInfo.categories)
        holder.bookDateTextView.text = String.format(Locale.getDefault(), context.getString(R.string.date_format_res), books[position].items[position].volumeInfo.publishedDate)
        holder.bookPagesTextView.text = String.format(Locale.getDefault(), context.getString(R.string.pages_format_res), books[position].items[position].volumeInfo.pageCount)
        holder.bookRatingBar.rating = books[position].items[position].volumeInfo.averageRating.toFloat()

        val imageResourceUrl = books[position].items[position].volumeInfo.imageLinks.thumbnail
        val placeholder = R.drawable.book_placeholder

//        Log.v("recycler", books[position].items[position].volumeInfo.imageLinks.thumbnail.toString()) ->
//
//        -> "http://books.google.com/books/content?id=VNIiAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
//
//         Glide
//            .with(context)
//            .load("http://books.google.com/books/content?id=VNIiAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
//            .placeholder(placeholder)
//            .into(holder.bookImageView) -> it works fine

        Glide
            .with(context)
            .load(imageResourceUrl)
            .placeholder(placeholder)
            .into(holder.bookImageView)
//        gives an error -> Load failed for http://books.google.com/books/content?id=VNIiAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api with size [382x495]
    }

    override fun getItemCount(): Int {
        return books.size
    }
}
