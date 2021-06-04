package com.erzhan.books

import android.content.Context
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
        holder.bookTitleTextView.text = books[position].title
        holder.bookAuthorTextView.text = String.format(Locale.getDefault(), context.getString(R.string.author_format_res), books[position].author)
        holder.bookGenreTextView.text = String.format(Locale.getDefault(), context.getString(R.string.genre_format_res), books[position].genre)
        holder.bookDateTextView.text = String.format(Locale.getDefault(), context.getString(R.string.date_format_res),books[position].date)
        holder.bookPagesTextView.text = String.format(Locale.getDefault(), context.getString(R.string.pages_format_res),books[position].numberOfPages.toString())
        holder.bookRatingBar.rating = books[position].rating.toFloat()

        val imageResourceUrl = books[position].imageSource
        val placeholder = R.drawable.book_placeholder

        Glide
            .with(context)
            .load(imageResourceUrl)
            .placeholder(placeholder)
            .into(holder.bookImageView)
    }

    override fun getItemCount(): Int {
        return books.size
    }
}
