package com.erzhan.books

class Book constructor(title: String, author: String, genre: String, date: String, imageSource: String, numberOfPages: Int?, rating: Double, infoLink: String) {

    var title = ""
        get() {
            return field
        }
    var author = ""
        get() {
            return field
        }
    var genre = ""
        get() {
            return field
        }

    var date = ""
        get() {
            return field
        }
    var imageSource = ""
        get() {
            return field
        }
    var numberOfPages: Int? = null
        get() {
            return field
        }
    var rating = -1.0
        get() {
            return field
        }

    var infoLink = ""
        get() {
            return field
        }

    init {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.date = date
        this.imageSource = imageSource;
        this.numberOfPages = numberOfPages;
        this.rating = rating;
        this.infoLink = infoLink;
    }
}