package com.erzhan.books

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.*
import kotlin.collections.ArrayList

class BookRecyclerViewAdapter(books: ArrayList<VolumeInfo>, listener: OnItemClickListener) :
    RecyclerView.Adapter<BookRecyclerViewAdapter.MyViewHolder>() {

    var books: ArrayList<VolumeInfo>
    val listener: OnItemClickListener

    lateinit var context: Context;

    init {
        this.books = books
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val bookTitleTextView: TextView
        val bookAuthorTextView: TextView
        val bookGenreTextView: TextView
        val bookDateTextView: TextView
        val bookImageView: ImageView
        val bookPagesTextView: TextView
        val bookRatingBar: RatingBar
        var infoLink: String

        init {
            bookTitleTextView = itemView.findViewById(R.id.bookTitleTextViewId)
            bookAuthorTextView = itemView.findViewById(R.id.bookAuthorTextViewId)
            bookGenreTextView = itemView.findViewById(R.id.bookGenreTextViewId)
            bookDateTextView = itemView.findViewById(R.id.bookDateTextViewId)
            bookImageView = itemView.findViewById(R.id.bookImageViewId)
            bookPagesTextView = itemView.findViewById(R.id.bookPagesTextViewId)
            bookRatingBar = itemView.findViewById(R.id.bookRatingBarId)
            infoLink = ""
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, infoLink)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: String)
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
        holder.infoLink = books[position].volumeInfo.infoLink


        lateinit var imageResourceUrl: String;

        if (books[position].volumeInfo.imageLinks.thumbnail != null) {
            imageResourceUrl = books[position].volumeInfo.imageLinks.thumbnail.toString()
        }
        val placeholder = R.drawable.book_placeholder

        Glide
            .with(context)
            .load(imageResourceUrl)
            .placeholder(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(holder.bookImageView)
    }


    override fun getItemCount(): Int {
        return books.size
    }
}
