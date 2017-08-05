package com.malcolmcrum.photosource.local

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.exif.GpsDirectory
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.malcolmcrum.photolocation.Constants
import com.malcolmcrum.photosource.Photo
import com.malcolmcrum.photosource.PhotoSource
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence




class LocalPhotoSource(kodein: Kodein) : PhotoSource {

    private val folderName: String = kodein.instance(Constants.LOCAL_FOLDER_LOCATION)

    override fun getPhotos(): Sequence<Photo> {
        return Files.walk(Paths.get(folderName))
                .asSequence()
                .filter { path -> path.endsWith(".jpg") }
                .map { path -> toPhoto(path) }
    }

    private fun toPhoto(path: Path): Photo {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val gps: Directory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java)
        val latRef = gps.getTagName(GpsDirectory.TAG_LATITUDE_REF)
        val lat = gps.getTagName(GpsDirectory.TAG_LATITUDE)
        val lonRef = gps.getString(GpsDirectory.TAG_LONGITUDE_REF)
        val lon = gps.getString(GpsDirectory.TAG_LONGITUDE)
        return Photo(lat.toDouble(), lon.toDouble())
    }
}