package com.malcolmcrum.locationquery

import org.junit.Before
import org.junit.Test
import java.io.File

class BoundaryProviderManualTest {
    private lateinit var boundaryProvider: BoundaryProvider

    @Before
    fun setUp() {
        boundaryProvider = BoundaryProvider(File("/Users/crummy/Downloads/ne_10m_admin_1_states_provinces/ne_10m_admin_1_states_provinces.shp"))
    }

    @Test
    fun getSanFrancisco() {
        val coordinates = Box(Point(37.75, -122.4), Point(37.80, -122.35))
        val boundaries = boundaryProvider.getBoundaries(coordinates)
        println(boundaries.map { it.name })
    }

    @Test
    fun getGermany() {
        val coordinates = Box(Point(54.438103, 6.849976), Point(47.294134, 14.430542))
        val boundaries = boundaryProvider.getBoundaries(coordinates)
        println(boundaries.map { it.name })
    }

}