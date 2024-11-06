package org.hash

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
}
