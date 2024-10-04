package org.search

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SimpleSearchTest {

    @Test fun singleElementArray_hit() {
        val search = SimpleSearch(arrayOf("nemo"))
        val index = search.find("nemo")
        assertEquals(0, index)
    }

    @Test fun singleElementArray_miss() {
        val search = SimpleSearch(arrayOf("dory"))
        assertFailsWith<RuntimeException> (
            message = "Needle nemo not found in haystack.",
            block = {
                search.find("nemo")
            }
        )
    }

    @Test fun tenElementArray_hit() {
        val haystack = Array<String>(10){"$it"}
        haystack[4] = "nemo"
        val search = SimpleSearch(haystack)
        val index = search.find("nemo")
        assertEquals(4, index)
    }

    @Test fun tenElementArray_miss() {
        val haystack = Array<String>(10){"$it"}
        val search = SimpleSearch(haystack)
        assertFailsWith<RuntimeException> (
            message = "Needle nemo not found in haystack.",
            block = {
                search.find("nemo")
            }
        )
    }

    @Test fun extraLargeElementArray_hit() {
        val haystack = Array<String>(10_000){"$it"}
        haystack[4] = "nemo"
        val search = SimpleSearch(haystack)
        val index = search.find("nemo")
        assertEquals(4, index)
    }

    @Test fun extraLargeElementArray_miss() {
        val haystack = Array<String>(10_000){"$it"}
        val search = SimpleSearch(haystack)
        assertFailsWith<RuntimeException> (
            message = "Needle nemo not found in haystack.",
            block = {
                search.find("nemo")
            }
        )
    }
}