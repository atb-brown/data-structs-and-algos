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

        require(needleIndex >= 0) { "Needle $needle not found in haystack." }
        return needleIndex
    }
}
