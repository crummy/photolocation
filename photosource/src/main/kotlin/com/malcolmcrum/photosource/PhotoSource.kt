package com.malcolmcrum.photosource

interface PhotoSource {
    fun getPhotos(): Collection<Photo>
    fun getPhoto(id: Photo.Id): Photo?
    fun refresh()
}