package com.malcolmcrum.locationquery

import com.github.salomonbrys.kodein.*
import com.malcolmcrum.photolocation.commons.Configuration
import mu.KotlinLogging
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CORS
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.gson.GsonSupport
import org.jetbrains.ktor.request.uri
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.optionalParam
import org.jetbrains.ktor.routing.route
import java.io.File

private val log = KotlinLogging.logger {}

val kodein = Kodein {
    bind<Configuration>() with singleton { Configuration() }
    bind<BoundaryProvider>() with singleton { BoundaryProvider(File("/Users/crummy/Downloads/ne_10m_admin_1_states_provinces/ne_10m_admin_1_states_provinces.shp")) }
    bind<BoundaryFilter>() with provider { BoundaryFilter() }
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(GsonSupport) {
        setPrettyPrinting()
    }
    install(CORS) {
        anyHost()
    }
    install(Routing) {
        val boundaryProvider: BoundaryProvider = kodein.instance()
        val boundaryFilter: BoundaryFilter = kodein.instance()
        route("boundaries") {
            optionalParam("topLeft") {
                optionalParam("bottomRight") {
                    get {
                        val topLeft = call.parameters["topLeft"]!!.toPoint()
                        val bottomRight = call.parameters["bottomRight"]!!.toPoint()
                        val allBoundaries = boundaryProvider.getBoundaries(Box(topLeft, bottomRight))
                        val filteredBoundaries = boundaryFilter.filter(allBoundaries, 4)
                        call.respond(filteredBoundaries)
                        log.info { "${call.request.uri}: ${filteredBoundaries.size} boundaries" }
                    }
                }
            }
        }
    }
}

private val POINT_MATCHER = "(-?\\d+\\.?\\d*),(-?\\d+\\.?\\d*)".toRegex()

private fun String.toPoint(): Point {
    val matches = POINT_MATCHER.find(this) ?: throw IllegalArgumentException("Could not convert $this to Point")
    return Point(matches.groupValues[1].toDouble(), matches.groupValues[2].toDouble())
}