package com.malcolmcrum.locationquery

import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class BoundaryFilterTest {
    private lateinit var random: Random

    @Before
    fun setUp() {
        random = Random()
    }

    @Test
    fun testReturningAllResultsWithoutAConsistentScale() {
        val box = Box(Point(10.0, 10.0), Point(100.0, 100.0))
        val boundaries = listOf(
                Boundary(emptyList(), "foo", pointInside(box), 1),
                Boundary(emptyList(), "foo", pointInside(box), 2),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3)
        )
        val filtered = BoundaryFilter().filter(boundaries, 4)

        assertEqual(boundaries, filtered)
    }

    @Test
    fun testFilteringScale() {
        val box = Box(Point(10.0, 10.0), Point(100.0, 100.0))
        val boundaries = listOf(
                Boundary(emptyList(), "foo", pointInside(box), 1),
                Boundary(emptyList(), "foo", pointInside(box), 2),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3)
        )
        val filtered = BoundaryFilter().filter(boundaries, 3)

        val scale3Boundaries = boundaries.filter { it.scaleRank == 3 }
        assertEqual(filtered, scale3Boundaries)
    }

    @Test
    fun testPreferringHigherScale() {
        val box = Box(Point(10.0, 10.0), Point(100.0, 100.0))
        val boundaries = listOf(
                Boundary(emptyList(), "foo", pointInside(box), 1),
                Boundary(emptyList(), "foo", pointInside(box), 2),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 3),
                Boundary(emptyList(), "foo", pointInside(box), 4)
        )
        val filtered = BoundaryFilter().filter(boundaries, 1)

        val scale4Boundaries = boundaries.filter { it.scaleRank == 4 }
        assertEqual(filtered, scale4Boundaries)
    }


    private fun assertEqual(boundaries: Collection<Any>, filtered: Collection<Any>) {
        assert(boundaries.size == filtered.size, { "First collection has ${boundaries.size} elements, but the second has ${filtered.size}"})
        boundaries.forEach { boundary -> assert(filtered.contains(boundary)) }
        filtered.forEach { boundary -> assert(boundaries.contains(boundary)) }
    }

    private fun pointInside(box: Box): Point {
        val x = ThreadLocalRandom.current().nextDouble(box.topLeft.x, box.bottomRight.x)
        val y = ThreadLocalRandom.current().nextDouble(box.topLeft.y, box.bottomRight.y)
        return Point(x, y)
    }

}