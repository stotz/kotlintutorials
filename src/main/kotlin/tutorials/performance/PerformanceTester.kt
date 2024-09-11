package ch.typedef.tutorials.performance

import java.util.concurrent.atomic.AtomicLong

class PerformanceTester {
    private var totalCalls = AtomicLong(0)
    private var totalTimeMillis = 0L

    fun testByCalls(calls: Long, testLambda: () -> Unit): PerformanceResult {
        reset()
        val startTime = System.currentTimeMillis()
        repeat(calls.toInt()) {
            testLambda()
            totalCalls.incrementAndGet()
        }
        totalTimeMillis = System.currentTimeMillis() - startTime
        return getResult()
    }

    fun testByTime(maxTimeMillis: Long, testLambda: () -> Unit): PerformanceResult {
        reset()
        val startTime = System.currentTimeMillis()
        var currentTime = startTime
        while (currentTime - startTime < maxTimeMillis) {
            testLambda()
            totalCalls.incrementAndGet()
            currentTime = System.currentTimeMillis()
        }
        totalTimeMillis = currentTime - startTime
        return getResult()
    }

    private fun reset() {
        totalCalls.set(0) // Reset totalCalls
        totalTimeMillis = 0L // Reset totalTimeMillis
    }

    private fun getResult(): PerformanceResult {
        val callsPerMillisecond = if (totalTimeMillis > 0) totalCalls.toDouble() / totalTimeMillis else 0.0
        return PerformanceResult(totalCalls.get(), totalTimeMillis, callsPerMillisecond)
    }

    data class PerformanceResult(val calls: Long, val millis: Long, val callsPerMillis: Double)
}

fun main() {
    val tester = PerformanceTester()

    // Test with number of calls
    val resultByCalls = tester.testByCalls(10000) {
        // Your test code here
        for (i in 1..1000) {
            Math.sqrt(i.toDouble())
        }
    }
    println("By Calls -> Total Calls: ${resultByCalls.calls}, Time: ${resultByCalls.millis}ms, Calls/ms: ${resultByCalls.callsPerMillis}")

    // Test with max time in milliseconds
    val resultByTime = tester.testByTime(1_000) {
        // Your test code here
        for (i in 1..1000) {
            Math.sqrt(i.toDouble())
        }
    }
    println("By Time -> Total Calls: ${resultByTime.calls}, Time: ${resultByTime.millis}ms, Calls/ms: ${resultByTime.callsPerMillis}")

    repeat(10) {
        val resultByTimeLong = tester.testByTime(10_000) {
            // Your test code here
            var longValue: Long = 0L
            for (i in 1..1000) {
                ++longValue
            }
        }
        println("Long by Time  -> Total Calls: ${resultByTimeLong.calls}, Time: ${resultByTimeLong.millis}ms, Calls/ms: ${resultByTimeLong.callsPerMillis}")

        val resultByTimeULong = tester.testByTime(10_000) {
            // Your test code here
            var longValue: ULong = 0UL
            for (i in 1..1000) {
                ++longValue
            }
        }
        println("ULong by Time -> Total Calls: ${resultByTimeULong.calls}, Time: ${resultByTimeULong.millis}ms, Calls/ms: ${resultByTimeULong.callsPerMillis}")
    }
}
