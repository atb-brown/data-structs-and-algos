package org.hash

import org.util.TestDataRunner
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HashSetTest {
    @Test
    fun brokenCollisionChain() {
        val mySet = TroubleshootingHashSet(false)

        // Insider information: The implementation uses modulo 9,999
        val elementA = 1
        val elementB = elementA + 9_999
        val elementC = elementB + 9_999

        mySet.add(elementA)
        mySet.add(elementB)
        mySet.add(elementC)

        assertTrue(mySet.contains(elementA))
        assertTrue(mySet.contains(elementB))
        assertTrue(mySet.contains(elementC))

        // Removing this element could present an edge case in a linear probing implementation.
        mySet.remove(elementA)

        assertFalse(mySet.contains(elementA))
        assertTrue(mySet.contains(elementB))
        assertTrue(mySet.contains(elementC))

        mySet.remove(elementB)

        assertFalse(mySet.contains(elementA))
        assertFalse(mySet.contains(elementB))
        assertTrue(mySet.contains(elementC))
    }

    @Test
    fun addDuplicates() {
        val mySet = TroubleshootingHashSet(false)

        // Adding the same element multiple times shouldn't cause issues.
        mySet.add(5)
        mySet.add(5)
        mySet.add(5)
        mySet.add(5)

        assertTrue(mySet.contains(5))

        mySet.remove(5)

        assertFalse(mySet.contains(5))
    }

    @Test
    fun largeTest() {
        val jsonReader = TestDataRunner("/org/hash/hashSetTestData.json")

        val mySet = TroubleshootingHashSet(false)
        jsonReader.forEach { operation, params ->
            val key = params[0].toInt()
            return@forEach doOperation(mySet, operation, key)
        }
    }

    private fun doOperation(
        mySet: TroubleshootingHashSet,
        operation: String,
        key: Int,
    ): String {
        return when (operation) {
            "add" -> {
                mySet.add(key)
                "null"
            }

            "contains" -> {
                mySet.contains(key).toString()
            }

            "remove" -> {
                mySet.remove(key)
                "null"
            }

            else -> error("Unrecognized operation.")
        }
    }
}
