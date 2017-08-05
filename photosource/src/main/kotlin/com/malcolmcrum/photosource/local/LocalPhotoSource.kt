package com.malcolmcrum.photosource.local

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.exif.GpsDirectory
import com.malcolmcrum.photosource.Configuration
import com.malcolmcrum.photosource.Photo
import com.malcolmcrum.photosource.PhotoSource
import com.malcolmcrum.photosource.toLatLon
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence

class LocalPhotoSource(config: Configuration) : PhotoSource {

    init {
        refresh()
    }

    private val photos: MutableSet<Photo> = HashSet()

    private val folderName: String = config.get(config.LOCAL_PHOTO_SOURCE_FOLDER)

    override fun refresh() {
        Files.walk(Paths.get(folderName))
                .asSequence()
                .filter { path -> path.endsWith(".jpg") }
                .map { path -> toPhoto(path) }
                .forEach { photo -> photos.add(photo) }
    }

    override fun getPhotos(): Collection<Photo> {
        return photos
    }

    private fun toPhoto(path: Path): Photo {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val gps: Directory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java)
        val latRef = gps.getTagName(GpsDirectory.TAG_LATITUDE_REF)
        val lat = gps.getTagName(GpsDirectory.TAG_LATITUDE)
        val lonRef = gps.getString(GpsDirectory.TAG_LONGITUDE_REF)
        val lon = gps.getString(GpsDirectory.TAG_LONGITUDE)
        val latLon = toLatLon(latRef, lat, lonRef, lon)
        return Photo(latLon.lat, latLon.lon)
    }

}