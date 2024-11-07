package org.search

import org.util.TimedEvent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SimpleSearchTest {
    @Test
    fun singleElementArray_hit() {
        val search = SimpleSearch(arrayOf("nemo"))

        val before = TimedEvent()
        val index = search.find("nemo")
        println("Search took ${before.timeSince()}")

        assertEquals(0, index)
    }

    @Test
    fun singleElementArray_miss() {
        val search = SimpleSearch(arrayOf("dory"))
        val iae =
            assertFailsWith<IllegalArgumentException>(
                block = {
                    search.find("nemo")
                },
            )
        assertEquals("Needle nemo not found in haystack.", iae.message)
    }

    @Test
    fun tenElementArray_hit() {
        val haystack = Array(10) { "$it" }
        haystack[4] = "nemo"
        val search = SimpleSearch(haystack)

        val before = TimedEvent()
        val index = search.find("nemo")
        println("Search took ${before.timeSince()}")

        assertEquals(4, index)
    }

    @Test
    fun tenElementArray_miss() {
        val haystack = Array(10) { "$it" }
        val search = SimpleSearch(haystack)
        val iae =
            assertFailsWith<IllegalArgumentException>(
                block = {
                    search.find("nemo")
                },
            )
        assertEquals("Needle nemo not found in haystack.", iae.message)
    }

    @Test
    fun extraLargeElementArray_hit() {
        val haystack = Array(10_000) { "$it" }
        haystack[4] = "nemo"
        val search = SimpleSearch(haystack)
        val index = search.find("nemo")
        assertEquals(4, index)
    }

    @Test
    fun extraLargeElementArray_miss() {
        val haystack = Array(10_000) { "$it" }
        val search = SimpleSearch(haystack)
        val iae =
            assertFailsWith<IllegalArgumentException>(
                block = {
                    search.find("nemo")
                },
            )
        assertEquals("Needle nemo not found in haystack.", iae.message)
    }
}
