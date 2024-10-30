package org.hash

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
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

    // TODO: Simplify this test case.
    @Test
    fun largeTest() {
        // TODO: Make Json parsing easier with a class.
        val rawJson = TroubleshootingHashSet::class.java.getResource("/org/hash/hashSetTestData.json")!!.readText()
        val json = Json.parseToJsonElement(rawJson).jsonObject

        val mySet = TroubleshootingHashSet(false)
        val testCases = json.get("testFunction")?.jsonArray?.size ?: -1
        for (i in 1..<testCases) {
            val testFunction = json.get("testFunction")!!.jsonArray.get(i).jsonPrimitive.content
            val param1 = json.get("testParam1")!!.jsonArray.get(i - 1).jsonPrimitive.int
            val expectedOutput = json.get("expectedOutput")!!.jsonArray.get(i).jsonPrimitive.content

            val output =
                when (testFunction) {
                    "add" -> {
                        mySet.add(param1)
                        "null"
                    }

                    "contains" -> {
                        mySet.contains(param1).toString()
                    }

                    "remove" -> {
                        mySet.remove(param1)
                        "null"
                    }

                    else -> "$testFunction : ❌❌❌❌❌"
                }

            assertEquals(expectedOutput, output)
        }
    }
}
