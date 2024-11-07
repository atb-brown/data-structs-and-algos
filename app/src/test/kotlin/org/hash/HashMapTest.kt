package org.hash

import org.util.TestDataRunner
import kotlin.test.Test
import kotlin.test.assertEquals

class HashMapTest {
    @Test
    fun simpleTest() {
        val myMap = TroubleshootingHashMap()

        myMap.put(1, 1)
        myMap.put(2, 2)
        assertEquals(1, myMap.get(1))
        assertEquals(-1, myMap.get(3))
        myMap.put(2, 1)
        assertEquals(1, myMap.get(2))
        myMap.remove(2)
        assertEquals(-1, myMap.get(2))
    }

    @Test
    fun largeTest() {
        val jsonReader = TestDataRunner("/org/hash/hashMapTestData.json")

        val myMap = TroubleshootingHashMap()
        jsonReader.forEach { operation, params ->
            val key = params[0].toInt()
            val value =
                if ("put".equals(operation)) {
                    params[1].toInt()
                } else {
                    0
                }
            return@forEach doOperation(myMap, operation, key, value)
        }
    }

    private fun doOperation(
        myMap: TroubleshootingHashMap,
        operation: String,
        key: Int,
        value: Int,
    ): String {
        return when (operation) {
            "put" -> {
                myMap.put(key, value)
                "null"
            }

            "get" -> {
                myMap.get(key).toString()
            }

            "remove" -> {
                myMap.remove(key)
                "null"
            }

            else -> error("Unrecognized operation.")
        }
    }
}
