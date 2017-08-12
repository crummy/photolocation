package com.malcolmcrum.photosource

import mu.KotlinLogging

data class LatLon(val lat: Double, val lon: Double)

private val DEGREES_MINUTES_SECONDS = "(-?\\d+)° (\\d+)' (\\d+\\.?\\d*)\"".toRegex()
private val FRACTIONS = "(\\d+)/(\\d+), (\\d+)/(\\d+), (\\d+)/(\\d+)".toRegex()
private val DECIMAL = "(-?\\d+\\.?\\d*)".toRegex()
private val log = KotlinLogging.logger {}

fun toLatLon(latRef: String, lat: String, lonRef: String, lon: String): LatLon {
    val latLon =  when {
        lat.matches(DECIMAL) && lon.matches(DECIMAL) -> LatLon(lat.toDouble(), lon.toDouble())
        lat.matches(DEGREES_MINUTES_SECONDS) && lon.matches(DEGREES_MINUTES_SECONDS) -> LatLon(toDecimal(lat), toDecimal(lon))
        lat.matches(FRACTIONS) && lon.matches(FRACTIONS) -> LatLon(fractionsToDecimal(lat), fractionsToDecimal(lon))
        else -> throw ParsingException("Couldn't parse $lat and $lon to lat/lon")
    }
    log.debug { "Parsed $lat, $lon to $latLon" }
    return latLon
}

fun fractionsToDecimal(fractions: String): Double {
    val matches = FRACTIONS.find(fractions)
    with(matches?.groupValues!!) {
        val degrees = get(1).toDouble() / get(2).toInt()
        val minutes = get(3).toDouble() / get(4).toInt()
        val seconds = get(5).toDouble() / get(6).toInt()
        return degrees + (minutes / 60) + (seconds / 3600)
    }
}

fun toDecimal(degreesMinutesSeconds: String): Double {
    val matches = DEGREES_MINUTES_SECONDS.find(degreesMinutesSeconds)
    with (matches?.groupValues!!) {
        val degrees = get(1).toDouble()
        val minutes = get(2).toDouble()
        val seconds = get(3).toDouble()
        val absoluteDecimal = Math.abs(degrees) + (minutes / 60) + (seconds / 3600)
        if (degrees < 0) {
            return -absoluteDecimal
        } else {
            return absoluteDecimal
        }
    }
}

class ParsingException(message: String) : RuntimeException(message)