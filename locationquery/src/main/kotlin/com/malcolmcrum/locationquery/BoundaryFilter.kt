package com.malcolmcrum.locationquery

import mu.KotlinLogging

private val SCALE_RANGE = 0..19
private val log = KotlinLogging.logger {}

class BoundaryFilter {
    fun filter(allBoundaries: Collection<Boundary>, minBoundaries: Int): Collection<Boundary> {
        val sorted = allBoundaries.sortedBy { boundary -> boundary.scaleRank }
        for (scale in SCALE_RANGE) {
            val filtered = sorted.filter { boundary -> boundary.scaleRank == scale }
            if (filtered.size >= minBoundaries) {
                log.info { "Found ${filtered.size} boundaries at scale $scale" }
                return filtered
            }
        }
        log.info { "Didn't find $minBoundaries boundaries at any scale. Returning entire collection" }
        return sorted
    }
}