package com.malcolmcrum.photosource

import org.junit.Test

internal class ConfigurationTest {
    @Test(expected = MissingConfigurationException::class)
    fun testMissingConfigurationThrowsException() {
        val config = Configuration()
        config.get("made-up-configuration")
    }
}