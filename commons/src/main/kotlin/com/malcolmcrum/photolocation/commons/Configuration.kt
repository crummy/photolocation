package com.malcolmcrum.photolocation.commons

class Configuration {
    val LOCAL_PHOTO_SOURCE_FOLDER = "local.photo.source.folder"

    private val settings = HashMap<String, String>()

    init {
        settings.putAll(System.getenv())
    }

    fun get(key: String): String {
        return settings[key] ?: throw MissingConfigurationException(key)
    }
}

class MissingConfigurationException(key: String) : Throwable("Missing required configuration: $key")
