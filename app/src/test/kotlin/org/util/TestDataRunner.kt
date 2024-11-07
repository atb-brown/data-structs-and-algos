package org.util

import kotlinx.serialization.json.Json
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
    private val rawJson = this::class.java.getResource(file)!!.readText()
    private val json = Json.parseToJsonElement(rawJson).jsonObject

    private val testOperations = json["testOperations"]!!.jsonArray
    private val expectedOutput = json["expectedOutput"]!!.jsonArray

    private fun getNumTestCases(): Int {
        return testOperations.size
    }

    private fun getOperation(index: Int): String {
        return testOperations.get(index).jsonPrimitive.content
    }

    private fun getParameter(
        index: Int,
        paramNum: Int,
    ): String {
        return json["testParam$paramNum"]!!.jsonArray.get(index).jsonPrimitive.content
    }

    private fun parameterExists(paramNum: Int): Boolean {
        return json["testParam$paramNum"] != null
    }

    private fun getExpectedOutput(index: Int): String {
        return expectedOutput.get(index).jsonPrimitive.content
    }

    fun forEach(run: TestOperationRun) {
        var paramCount = 0
        while (parameterExists(paramCount + 1)) {
            paramCount++
        }

        for (testCase in 1..<getNumTestCases()) {
            val paramList = buildParamList(testCase, paramCount)
            runTestCase(testCase, paramList, run)
        }
    }

    private fun buildParamList(
        testCase: Int,
        paramCount: Int,
    ): ArrayList<String> {
        var paramList = ArrayList<String>()
        for (p in 1..paramCount) {
            paramList.add(getParameter(testCase - 1, p))
        }

        return paramList
    }

    private fun runTestCase(
        testCase: Int,
        paramList: ArrayList<String>,
        run: TestOperationRun,
    ) {
        val output = run(getOperation(testCase), paramList)
        assertEquals(
            getExpectedOutput(testCase),
            output,
            "TestCase $testCase: ${getOperation(testCase)}(${paramList.joinToString(", ")}) Expected ${
                getExpectedOutput(
                    testCase,
                )
            }",
        )
    }
}
