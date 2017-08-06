package com.malcolmcrum.photosource

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.malcolmcrum.photosource.local.LocalPhotoSource
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.gson.GsonSupport
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.route


val kodein = Kodein {
    bind<Configuration>() with singleton { Configuration() }
    bind<PhotoSource>() with singleton { LocalPhotoSource(instance()) }
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(GsonSupport) {
        setPrettyPrinting()
    }
    install(Routing) {
        route("photos") {
            val photoSource: PhotoSource = kodein.instance()
            get {
                call.respond(photoSource.getPhotos())
            }
            get(":id") {
                val id = Photo.Id(call.parameters["id"]!!)
                photoSource.getPhoto(id).apply { call.respond(it) } ?: call.response.status(HttpStatusCode.NotFound)
            }
            get("refresh") {
                photoSource.refresh()
            }
        }
    }
}