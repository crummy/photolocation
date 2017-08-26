package com.malcolmcrum.locationquery

data class Point(val y: Double, val x: Double)

data class Box(val topLeft: Point, val bottomRight: Point)