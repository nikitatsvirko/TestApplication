package com.application.nikita.testapplication.models

import com.activeandroid.query.Delete
import com.activeandroid.query.Select

class PhotoDao {

    fun createPhoto(): Photo {
        val photo = Photo()
        photo.save()
        return photo
    }

    fun savePhoto(photo: Photo) {
        photo.save()
    }

    fun loadPhotos() = Select().from(Photo::class.java).execute<Photo>()

    fun deletePhoto(photo: Photo) {
        photo.delete()
    }

    fun deleteAllPhotos() {
        Delete().from(Photo::class.java).execute<Photo>()
    }
}
