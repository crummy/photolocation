package com.malcolmcrum.locationquery

import com.github.salomonbrys.kodein.Kodein
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.gson.GsonSupport
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.route

val kodein = Kodein {
    //bind<Configuration>() with singleton { Configuration() }
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(GsonSupport) {
        setPrettyPrinting()
    }
    install(Routing) {
        route("photos") {
            get {

            }
        }
    }
}