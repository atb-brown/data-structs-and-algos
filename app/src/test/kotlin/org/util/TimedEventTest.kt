package org.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TimedEventTest {
    @Test fun timeSinceSelf() {
        val init = TimedEvent()
        Thread.sleep(10)
        // Will be at least 10m, but should be less than 20ms.
        assertTrue(Regex("1[0-9]ms").matches(init.timeSince()))
    }

    @Test fun notimeSinceSelf() {
        assertEquals("0ms", TimedEvent().timeSince())
    }

    @Test fun timeSince() {
        val init = TimedEvent()
        Thread.sleep(10)
        val final = TimedEvent()
        // Will be at least 10m, but should be less than 20ms.
        assertTrue(Regex("1[0-9]ms").matches(final.timeSince(init)))
    }

    @Test fun noTimeSince() {
        assertEquals("0ms", TimedEvent().timeSince(TimedEvent()))
    }
}
