package org.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.assertEquals

typealias TestOperationRun = (operation: String, params: ArrayList<String>) -> String

/**
 * This class can be used to run test cases that are defined in json files. This is particularly helpful for large data
 * sets.
 */
class TestDataRunner(file: String) {
    companion object {
        private val KEYS =
            ArrayList(
                listOf(
                    "testOperations",
                    "testParams",
                    "expectedOutput",
                ),
            )

        private const val TEST_OPS = 0
        private const val TEST_PARAMS = 1
        private const val EXP_OUTPUT = 2
    }

    private val rawJson = this::class.java.getResource(file)!!.readText()
    private val json = Json.parseToJsonElement(rawJson).jsonObject

    private val testOperations = requiredJsonArray(TEST_OPS)
    private val testParams = requiredJsonArray(TEST_PARAMS)
    private val expectedOutput = requiredJsonArray(EXP_OUTPUT)

    private fun requiredJsonArray(keyIndex: Int): JsonArray {
        val key = KEYS[keyIndex]
        requireNotNull(json[key]) {
            val unexpectedKeys = "\"${json.keys.filter { s -> !KEYS.contains(s) }.joinToString(", ")}\""
            "Test file did not have a(n) \"$key\" array. Is there a typo in one of this/these key(s)? $unexpectedKeys"
        }
        return json[key]!!.jsonArray
    }

    private fun getNumTestCases(): Int {
        return testOperations.size
    }

    private fun getTestParams(index: Int): ArrayList<String> {
        return ArrayList(testParams[index].jsonArray.toList().map { it.jsonPrimitive.content })
    }

    private fun getOperation(index: Int): String {
        return testOperations[index].jsonPrimitive.content
    }

    private fun getExpectedOutput(index: Int): String {
        return expectedOutput[index].jsonPrimitive.content
    }

    fun forEach(run: TestOperationRun) {
        for (testCase in 1..<getNumTestCases()) {
            runTestCase(testCase, run)
        }
    }

    private fun runTestCase(
        testCase: Int,
        run: TestOperationRun,
    ) {
        val output = run(getOperation(testCase), getTestParams(testCase))
        assertEquals(
            getExpectedOutput(testCase),
            output,
            "TestCase $testCase: ${getOperation(testCase)}(${getTestParams(testCase).joinToString(", ")}) Expected ${
                getExpectedOutput(
                    testCase,
                )
            }",
        )
    }
}
