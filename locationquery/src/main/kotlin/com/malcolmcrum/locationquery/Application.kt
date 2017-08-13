package com.malcolmcrum.locationquery

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.malcolmcrum.photolocation.commons.Configuration
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.gson.GsonSupport
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.param
import org.jetbrains.ktor.routing.route
import java.io.File

val kodein = Kodein {
    bind<Configuration>() with singleton { Configuration() }
    bind<BoundaryProvider>() with singleton { BoundaryProvider(File("/Users/crummy/Downloads/ne_10m_admin_1_states_provinces/ne_10m_admin_1_states_provinces.shp")) }
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(GsonSupport) {
        setPrettyPrinting()
    }
    install(Routing) {
        val boundaryProvider: BoundaryProvider = kodein.instance()
        route("boundaries") {
            param("topLeft") {
                param("bottomRight") {
                    get {
                        val topLeft = call.parameters["topLeft"]!!.toPoint()
                        val bottomRight = call.parameters["bottomRight"]!!.toPoint()
                        call.respond(boundaryProvider.getBoundaries(Box(topLeft, bottomRight)))
                    }
                }
            }
        }
    }
}

private val POINT_MATCHER = "([-]\\d+\\.\\d*),([-]\\d+\\.\\d*)".toRegex()

private fun String.toPoint(): Point {
    val matches = POINT_MATCHER.find(this) ?: throw IllegalArgumentException("Could not convert $this to Point")
    return Point(matches.groupValues[1].toDouble(), matches.groupValues[2].toDouble())
}