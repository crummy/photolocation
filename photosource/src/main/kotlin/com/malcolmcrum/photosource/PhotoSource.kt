package com.malcolmcrum.photosource

interface PhotoSource {
    fun getPhotos(): Sequence<Photo>
}