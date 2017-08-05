package com.malcolmcrum.photosource

interface PhotoSource {
    fun getPhotos(): Collection<Photo>
    fun getPhoto(id: Photo.Id)
    fun refresh()
}