package com.malcolmcrum.locationquery

data class Boundary(val polygon: List<Point>, val name: String, val center: Point, val scaleRank: Int)