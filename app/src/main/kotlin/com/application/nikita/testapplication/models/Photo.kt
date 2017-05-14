package com.application.nikita.testapplication.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

@Table(name = "Photo", id = "_id")
class Photo: Model {

    @Column(name = "date")
    var date: Long = 0
    @Column(name = "photoId")
    var id: Int = 0
    @Column(name = "lat")
    var lat: String? = null
    @Column(name = "lng")
    var lng: String? = null
    @Column(name = "url")
    var url: String? = null

    constructor(date: Long, id: Int, lat: String, lng: String, url: String) {
        this.date = date
        this.id = id
        this.lat = lat
        this.lng = lng
        this.url = url
    }

    constructor()

    fun getInfo(): String = "date: $date\n" +
            "id: $id\n" +
            "lat: $lat\n" +
            "lng: $lng\n" +
            "url: $url\n"
}