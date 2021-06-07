package com.erzhan.books

data class Book(
    val items: ArrayList<VolumeInfo>
)

data class VolumeInfo(
    val volumeInfo: BookInfo
)

data class BookInfo(
    val title: String,
    val authors: ArrayList<String>?,
    val genre: String,
    val publishedDate: String?,
    val pageCount: Int?,
    val categories: ArrayList<String>?,
    val averageRating: Double?,
    val imageLinks: ImageLink,
    val infoLink: String
)

data class ImageLink(
    val thumbnail: String?
)
