package com.malcolmcrum.photolocation.commons

data class Photo(
        val id: Id,
        val lat: Double,
        val lon: Double) {

    class Id (val value: String)
}