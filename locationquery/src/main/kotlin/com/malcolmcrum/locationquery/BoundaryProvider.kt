package com.malcolmcrum.locationquery

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.data.FileDataStoreFinder
import org.geotools.data.Query
import org.geotools.filter.text.cql2.CQL
import java.io.File
import java.util.*

private fun Coordinate.toPoint(): Point = Point(this.x, this.y)

private fun MultiPolygon.toPoints(): List<Point> = this.coordinates.map { coordinate -> coordinate.toPoint() }

class BoundaryProvider(shapeFile: File) {
    val store = FileDataStoreFinder.getDataStore(shapeFile)
    var source = store.featureSource

    fun getBoundaries(box: Box): Collection<Boundary> {
        val filter = CQL.toFilter("BBOX(the_geom, ${box.topLeft.lon}, ${box.topLeft.lat}, ${box.bottomRight.lon}, ${box.bottomRight.lat})")

        val query = Query("ne_10m_admin_1_states_provinces", filter)

        val features = source.getFeatures(query)

        val boundaries = ArrayList<Boundary>()
        features.features().use {
            while (it.hasNext()) {
                val feature = it.next()
                val polygon = feature.getAttribute("the_geom") as MultiPolygon
                val name = feature.getAttribute("name").toString()
                val center = Point(feature.getAttribute("latitude").toString().toDouble(),
                        feature.getAttribute("longitude").toString().toDouble())
                val boundary = Boundary(polygon.toPoints(), name, center)
                boundaries.add(boundary)
            }
        }

        return boundaries
    }
}