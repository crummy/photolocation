package com.malcolmcrum.photosource

import org.junit.Assert.assertEquals
import org.junit.Test

internal class LatLonTest {
    @Test
    fun testConvertSamples() {
        val latRef = "N"
        val lat = "54° 59' 22.8\""
        val lonRef = "W"
        val lon = "-1° 54' 51\""

        val latLon = toLatLon(latRef, lat, lonRef, lon)

        assertEquals(54.98966667, latLon.lat, 0.01)
        assertEquals(-1.91416667, latLon.lon, 0.01)
    }
}