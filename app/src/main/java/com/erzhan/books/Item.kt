package com.erzhan.books

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("volumeInfo")
    @Expose
    var volumeInfo: VolumeInfo? = null
}