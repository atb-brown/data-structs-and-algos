package org.util

import java.time.Instant

class TimedEvent {
    val ts = Instant.now().toEpochMilli()

    fun timeSince(): String {
        return "${Instant.now().toEpochMilli() - ts}ms"
    }

    fun timeSince(initialEvent: TimedEvent): String {
        return "${ts - initialEvent.ts}ms"
    }
}
