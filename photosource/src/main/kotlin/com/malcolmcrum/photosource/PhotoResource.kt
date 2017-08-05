package com.malcolmcrum.photosource

import io.javalin.Javalin

class PhotoResource(server: Javalin, photoSource: PhotoSource) {
    init {
        server.routes {
            path("photos") {
                get(photoSource::getPhotos)
                path(":id") {
                    get(id -> photoSource.getPhoto(id))
                }
                path("refresh") {
                    get(photoSource::refresh)
                }
            }
        }
    }
}