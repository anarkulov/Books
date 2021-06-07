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
import java.util.*
import kotlin.collections.ArrayList

class BookRecyclerViewAdapter(books: ArrayList<VolumeInfo>) :
    RecyclerView.Adapter<BookRecyclerViewAdapter.MyViewHolder>() {

    var books: ArrayList<VolumeInfo>

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
        holder.bookTitleTextView.text = books[position].volumeInfo.title
        if (books[position].volumeInfo.authors != null) {
            holder.bookAuthorTextView.text = String.format(
                Locale.getDefault(), context.getString(R.string.author_format_res),
                books[position].volumeInfo.authors?.get(0)
            )
        } else {
            holder.bookAuthorTextView.text = String.format(
                Locale.getDefault(), context.getString(R.string.author_format_res),
                context.getString(R.string.not_available_res)
            )
        }
        if (books[position].volumeInfo.categories != null) {
            holder.bookGenreTextView.text = books[position].volumeInfo.categories?.get(0)?.let {
                String.format(
                    Locale.getDefault(), context.getString(R.string.genre_format_res),
                    it
                )
            }
        } else {
            holder.bookGenreTextView.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.genre_format_res),
                context.getString(R.string.not_available_res)
            )
        }
        if (books[position].volumeInfo.publishedDate != null) {
            holder.bookDateTextView.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.date_format_res),
                books[position].volumeInfo.publishedDate
            )
        } else {
            holder.bookDateTextView.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.date_format_res),
                context.getString(R.string.not_available_res)
            )
        }
        if (books[position].volumeInfo.pageCount != null) {
            holder.bookPagesTextView.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.pages_format_res),
                books[position].volumeInfo.pageCount.toString()
            )

        } else {
            holder.bookPagesTextView.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.pages_format_res),
                context.getString(R.string.not_available_res)
            )
        }

        holder.bookRatingBar.rating = books[position].volumeInfo.averageRating?.toFloat() ?: 0.0f

        lateinit var imageResourceUrl: String;

        if (books[position].volumeInfo.imageLinks.thumbnail != null) {
            imageResourceUrl = books[position].volumeInfo.imageLinks.thumbnail.toString()
        }
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
