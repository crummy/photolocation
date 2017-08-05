package com.malcolmcrum.photosource

import com.github.salomonbrys.kodein.*
import com.malcolmcrum.photosource.local.LocalPhotoSource
import io.javalin.Javalin
import mu.KotlinLogging
import org.slf4j.Logger

fun main(args: Array<String>) {
    Application()
}

class Application : KodeinAware {
    override val kodein = Kodein {
        bind<Configuration>() with singleton { Configuration() }
        bind<Logger>() with multiton { cls: Class<*> -> KotlinLogging.logger(cls.simpleName) }
        bind<Javalin>() with singleton { Javalin.create().port(7000).start() }
        bind<PhotoSource>() with singleton { LocalPhotoSource(instance()) }
        bind<PhotoResource>() with singleton { PhotoResource(instance(), instance()) }
    }

    private val photoResource: PhotoResource = kodein.instance()
}
