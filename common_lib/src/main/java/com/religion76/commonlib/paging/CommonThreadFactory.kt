package com.religion76.commonlib.paging

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by SunChao on 2019-07-24.
 */
class CommonThreadFactory(prefix: String ?= null) : ThreadFactory {

    private val poolNumber = AtomicInteger(1)
    private var group: ThreadGroup
    private val threadNumber = AtomicInteger(1)
    private var namePrefix: String

    init {
        val s = System.getSecurityManager()
        group = if (s != null)
            s.threadGroup
        else
            Thread.currentThread().threadGroup
        namePrefix = prefix ?: "" +
                poolNumber.getAndIncrement() +
                "-thread-"

    }

    override fun newThread(r: Runnable): Thread {
        val t = Thread(
            group, r,
            namePrefix + threadNumber.getAndIncrement(),
            0
        )
        if (t.isDaemon)
            t.isDaemon = false
        if (t.priority != Thread.NORM_PRIORITY)
            t.priority = Thread.NORM_PRIORITY
        return t
    }
}