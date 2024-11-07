package org.hash

/**
 * This class will be submitted to leetcode.
 *
 * https://leetcode.com/explore/learn/card/hash-table/182/practical-applications/1139/
 */
open class MyHashSet {
    companion object {
        private const val BUCKET_SIZE = 10_000
        private val EMPTY = null
    }

    // Use an array list in order to avoid broken collision chains.
    private val buckets = Array<ArrayList<Int>?>(BUCKET_SIZE) { EMPTY }

    open fun add(key: Int) {
        val hash = hash(key)
        val bucket = buckets[hash]
        if (bucket == EMPTY) {
            buckets[hash] = ArrayList(listOf(key))
        } else if (!bucket.contains(key)) {
            // Don't add a duplicate key.
            bucket.add(key)
        }
    }

    open fun remove(key: Int) {
        val hash = hash(key)
        buckets[hash]?.remove(key)
    }

    open fun contains(key: Int): Boolean {
        val hash = hash(key)
        return buckets[hash]?.contains(key) ?: false
    }

    protected open fun hash(key: Int): Int {
        return key % (BUCKET_SIZE - 1)
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * var obj = MyHashSet()
 * obj.add(key)
 * obj.remove(key)
 * var param_3 = obj.contains(key)
 */
