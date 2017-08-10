package com.malcolmcrum.photosource

import com.malcolmcrum.photolocation.commons.Photo

interface PhotoSource {
    fun getPhotos(): Collection<Photo>
    fun getPhoto(id: Photo.Id): Photo?
    fun refresh()
}