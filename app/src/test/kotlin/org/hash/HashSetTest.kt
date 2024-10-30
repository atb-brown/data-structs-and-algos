package org.hash

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test

class HashSetTest {
    // TODO: Simplify this test and add more simple test cases.
    @Test fun largeTest() {
        // TODO: Make Json parsing easier with a class.
        val rawJson = TroubleshootingHashSet::class.java.getResource("/org/hash/hashSetTestData.json")!!.readText()
        val json = Json.parseToJsonElement(rawJson).jsonObject

        val mySet = TroubleshootingHashSet(true)
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

            if (!expectedOutput.equals(output)) {
                error("")
            }
        }
    }
}
