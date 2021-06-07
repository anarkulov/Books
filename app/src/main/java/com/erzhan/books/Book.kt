package com.erzhan.books

//import com.google.gson.annotations.Expose
//import com.google.gson.annotations.SerializedName
//
//class Book {
//    @SerializedName("items")
//    @Expose
//    var items: List<Item>? = null
//}


// Questions -> Можно ли просто как внизу использовать дата классы в одном файле ?
// Или как сверху использовать разные классы для разных данных из json ?

data class Book(
    val items: ArrayList<VolumeInfos>
)

data class VolumeInfos(
    val volumeInfo: BookInfo
)

data class BookInfo(
    val title: String,
    val authors: ArrayList<String>,
    val genre: String,
    val publishedDate: String,
    val pageCount: Int,
    val categories: ArrayList<String>,
    val averageRating: Double,
    val imageLinks: ImageLink,
    val infoLink: String
)

data class ImageLink(
    val thumbnail: String
)
