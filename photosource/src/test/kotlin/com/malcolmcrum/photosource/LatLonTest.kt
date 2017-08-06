package com.malcolmcrum.photosource

import org.junit.Assert.assertEquals
import org.junit.Test

internal class LatLonTest {
    @Test
    fun testConvertDegreesMinutesSeconds() {
        val latRef = "N"
        val lat = "54° 59' 22.8\""
        val lonRef = "W"
        val lon = "-1° 54' 51\""

        val latLon = toLatLon(latRef, lat, lonRef, lon)

        assertEquals(54.98966667, latLon.lat, 0.01)
        assertEquals(-1.91416667, latLon.lon, 0.01)
    }

    @Test
    fun testConvertFractions() {
        val latRef = "N"
        val lat = "42/1, 24/1, 2692/100"
        val lonRef = "E"
        val lon = "19/1, 48/1, 5183/100"

        val latLon = toLatLon(latRef, lat, lonRef, lon)
        assertEquals(42.407478, latLon.lat, 0.01)
        assertEquals(19.814397, latLon.lon, 0.01)
    }
}