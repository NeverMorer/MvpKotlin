package com.religion76.commonlib.paging

import java.util.concurrent.Executors

object ServiceLocator {
    val diskIo = Executors.newSingleThreadExecutor()
    val networkIo = Executors.newFixedThreadPool(5, CommonThreadFactory("common_"))
    val workers = Executors.newFixedThreadPool(2, CommonThreadFactory("worker_"))
}