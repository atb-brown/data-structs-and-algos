package org.search

class SimpleSearch(haystack: Array<String>) {
    val haystack = haystack

    // O(n) --> Linear Time
    fun find(needle: String): Int {
        var needleIndex = -1
        simpleLoop@ for (i in haystack.indices) {
            if (needle == haystack[i]) {
                println("""Found "$needle" at index: $i""")
                needleIndex = i
                break@simpleLoop
            }
        }

        if (needleIndex >= 0) {
            return needleIndex
        } else {
            throw RuntimeException("Needle $needle not found in haystack.")
        }
    }
}

fun main() {
//    SimpleSearch(arrayOf("nemo")).find("nemo")
//    SimpleSearch(arrayOf("dory", "bruce", "marlin", "nemo", "gill", "bloat", "nigel", "squirt", "darla", "hank")).find("nemo")
//    SimpleSearch(Array<String>(100000) { "nemo" }).find("nemo")
}
