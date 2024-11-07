package org.hash

/**
 * This class will be submitted to leetcode.
 *
 * https://leetcode.com/explore/learn/card/hash-table/182/practical-applications/1140/
 */
open class MyHashMap {
    companion object {
        private const val BUCKET_SIZE = 10_000
        private const val EMPTY = -1
        private val EMPTY_BUCKET = null
    }

    // Use an array list in order to avoid broken collision chains.
    private val buckets = Array<ArrayList<Element>?>(BUCKET_SIZE) { EMPTY_BUCKET }

    fun put(
        key: Int,
        value: Int,
    ) {
        val hash = hash(key)
        val bucket = buckets[hash]
        if (bucket == EMPTY_BUCKET) {
            buckets[hash] = ArrayList(listOf(Element(key, value)))
        } else {
            val element = getElement(key)
            if (element != null) {
                element.value = value
            } else {
                bucket.add(Element(key, value))
            }
        }
    }

    fun get(key: Int): Int {
        return getElement(key)?.value ?: EMPTY
    }

    private fun getElement(key: Int): Element? {
        var a: Element? = null
        ifFound(key) { e, _ -> a = e }
        return a
    }

    fun remove(key: Int) {
        ifFound(key) { e, b -> b.remove(e) }
    }

    protected open fun hash(key: Int): Int {
        return key % (BUCKET_SIZE - 1)
    }

    protected open fun ifFound(
        key: Int,
        ifTrue: (e: Element, b: ArrayList<Element>) -> Unit,
    ) {
        val hash = hash(key)
        val bucket = buckets[hash]

        var found: Element? = null
        bucket?.forEach { e ->
            if (key == e.key) {
                found = e
                return@forEach
            }
        }

        if (found != null) {
            ifTrue(found!!, bucket!!)
        }
    }

    protected open fun contains(key: Int): Boolean {
        return get(key) != EMPTY
    }

    class Element(val key: Int, var value: Int)
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * var obj = MyHashMap()
 * obj.put(key,value)
 * var param_2 = obj.get(key)
 * obj.remove(key)
 */
