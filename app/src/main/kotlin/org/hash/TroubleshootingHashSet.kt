package org.hash

/**
 * This class can be used to troubleshoot/debug [MyHashSet]
 */
class TroubleshootingHashSet(private val debug: Boolean) : MyHashSet() {
    override fun add(key: Int) {
        debug {
            println("Adding key $key")
            println("Already exists?: ${contains(key)}")
        }
        super.add(key)
    }

    override fun remove(key: Int) {
        debug {
            println("Removing key $key")
            "Did it exist before being removed?: ${contains(key)}"
        }
        super.remove(key)
    }

    override fun hash(key: Int): Int {
        val hash = super.hash(key)
        debug { println("Hash $key -> $hash") }
        return hash
    }

    private fun debug(runner: () -> Unit) {
        if (debug) {
            runner()
        }
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * var obj = MyHashSet()
 * obj.add(key)
 * obj.remove(key)
 * var param_3 = obj.contains(key)
 */
