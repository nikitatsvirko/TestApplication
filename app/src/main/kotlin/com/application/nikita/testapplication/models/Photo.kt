package com.application.nikita.testapplication.models

import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL

class Photo(date: Int, id: Int, lat: String, lng: String, url: String) {

    var date: Int
        get() = this.date
        set(value) {
            this.date = value
        }

    var id: Int
        get() = this.id
        set(value) {
            this.id = value
        }

    var lat: String
        get() = this.lat
        set(value) {
            this.lat = value
        }

    var lng: String
        get() = this.lng
        set(value) {
            this.lng = value
        }

    var url: String
        get() = this.url
        set(value) {
            this.url = value
        }

    fun getDrawableFromUrl(): Drawable? {
        try {
            val inputStream: InputStream = URL(url).content as InputStream
            val image = Drawable.createFromStream(inputStream, "image")
            return image
        }catch (e: Exception) {
            return null
        }
    }

    init {
        this.date = date
        this.id = id
        this.lat = lat
        this.lng = lng
        this.url = url
    }



}