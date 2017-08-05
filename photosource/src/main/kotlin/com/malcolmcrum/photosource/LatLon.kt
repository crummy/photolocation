package com.malcolmcrum.photosource

import mu.KotlinLogging

data class LatLon(val lat: Double, val lon: Double)

private val PATTERN = "(-?\\d+)° (\\d+)' (\\d+\\.?\\d*)\"".toRegex()
private val log = KotlinLogging.logger {} // TODO: how to inject this?

fun toLatLon(latRef: String, lat: String, lonRef: String, lon: String): LatLon {
    val latitude = toDecimal(lat)
    val longitude = toDecimal(lon)
    return LatLon(latitude, longitude)
}

fun toDecimal(degreesMinutesSeconds: String): Double {
    val matches = PATTERN.find(degreesMinutesSeconds)
    val degrees = matches?.groupValues?.get(1)?.toDouble() ?: 0.0
    val minutes = matches?.groupValues?.get(2)?.toDouble() ?: 0.0
    val seconds = matches?.groupValues?.get(3)?.toDouble() ?: 0.0
    log.debug { "Parsed $degreesMinutesSeconds to $degrees, $minutes, $seconds" }
    val absoluteDecimal = Math.abs(degrees) + (minutes / 60) + (seconds / 3600)
    if (degrees < 0) {
        return -absoluteDecimal
    } else {
        return absoluteDecimal
    }
}
