package com.malcolmcrum.locationquery

data class Point(val lat: Double, val lon: Double)

data class Box(val topLeft: Point, val bottomRight: Point)