package com.malcolmcrum.photosource.local

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.Directory
import com.drew.metadata.exif.GpsDirectory
import com.malcolmcrum.photosource.*
import mu.KotlinLogging
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence

class LocalPhotoSource(config: Configuration) : PhotoSource {
    private val log = KotlinLogging.logger {}
    private val folderName: String = config.get(config.LOCAL_PHOTO_SOURCE_FOLDER)
    private val photos: MutableMap<Photo.Id, Photo> = HashMap()

    override fun getPhoto(id: Photo.Id): Photo? {
        return photos[id]
    }

    override fun refresh() {
        log.info { "Refreshing photos in $folderName" }
        Files.walk(Paths.get(folderName))
                .asSequence()
                .onEach { path -> log.debug { "considering $path" } }
                .filter { path -> Files.isRegularFile(path) }
                .forEach { path -> addPhoto(path) }
    }

    override fun getPhotos(): Collection<Photo> {
        return photos.values
    }

    private fun addPhoto(path: Path) {
        try {
            log.debug { "Attempting to parse $path" }
            val metadata = ImageMetadataReader.readMetadata(path.toFile())
            val gps: Directory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java) ?: throw ImageProcessingException("Missing GPS data")
            val latRef = gps.getString(GpsDirectory.TAG_LATITUDE_REF)
            val lat = gps.getString(GpsDirectory.TAG_LATITUDE)
            val lonRef = gps.getString(GpsDirectory.TAG_LONGITUDE_REF)
            val lon = gps.getString(GpsDirectory.TAG_LONGITUDE)
            log.debug { "read latRef: $latRef, lat: $lat, lonRef: $lonRef, lon: $lon" }
            val latLon = toLatLon(latRef, lat, lonRef, lon)
            photos.put(Photo.Id(path.toString()), Photo(Photo.Id(path.toString()), latLon.lat, latLon.lon))
        } catch (e: ImageProcessingException) {
            log.info { "Failed to process file $path; maybe it's not a photo? ${e.message}" }
        } catch (e: IOException) {
            log.warn { "Failed to read file $path: ${e.message}" }
        } catch (e: ParsingException) {
            log.warn { "Failed to parse GPS data for $path: ${e.message}" }
        }
    }

}