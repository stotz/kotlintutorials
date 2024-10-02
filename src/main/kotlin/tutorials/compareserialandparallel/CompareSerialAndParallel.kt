package tutorials.compareserialandparallel

import java.util.concurrent.Executors
import java.util.concurrent.CountDownLatch
import kotlin.system.measureNanoTime
import kotlinx.coroutines.*

/**
 * Constant representing the time unit for the performance comparison.
 * Possible values are "ns" (nanoseconds), "µs" (microseconds), and "ms" (milliseconds).
 */
const val TIME_UNIT = "µs" // Change this to "ns", "µs", or "ms" for different time units

/**
 * Converts the given time in nanoseconds to the desired time unit.
 *
 * @param nanoTime The time in nanoseconds.
 * @param unit The desired time unit ("ns", "µs", "ms").
 * @return The time converted to the specified unit as a Double.
 */
fun convertToDesiredUnit(nanoTime: Long, unit: String): Double {
    return when (unit) {
        "ns" -> nanoTime.toDouble() // Already in nanoseconds
        "µs" -> nanoTime / 1_000.0 // Convert to microseconds
        "ms" -> nanoTime / 1_000_000.0 // Convert to milliseconds
        else -> nanoTime.toDouble() // Fallback to nanoseconds if unit is not recognized
    }
}

/**
 * Performs a step of serial processing by delaying execution based on the given nanoDelay.
 *
 * @param nanoDelay The delay in nanoseconds.
 * @return The delay value after adding 100 nanoseconds.
 */
fun stepSerial(nanoDelay: Long): Long {
    val delay = nanoDelay + 100
    Thread.sleep(delay / 1_000_000) // Convert to milliseconds
    return delay
}

/**
 * Performs a step of asynchronous processing by delaying execution using coroutines.
 *
 * @param nanoDelay The delay in nanoseconds.
 * @return The delay value after adding 100 nanoseconds.
 */
suspend fun stepAsynchron(nanoDelay: Long): Long {
    val delay = nanoDelay + 100
    delay(delay / 1_000_000) // Non-blocking delay in coroutines
    return delay
}

/**
 * Performs a step of thread-based processing by delaying execution using a thread.
 *
 * @param nanoDelay The delay in nanoseconds.
 * @param latch A CountDownLatch used to signal completion of the thread.
 * @return The delay value after adding 100 nanoseconds.
 */
fun stepThread(nanoDelay: Long, latch: CountDownLatch): Long {
    val delay = nanoDelay + 100
    Thread.sleep(delay / 1_000_000) // Convert to milliseconds
    latch.countDown() // Signal that this thread is done
    return delay
}

/**
 * Executes the serial processing by performing three sequential steps.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The total execution time in nanoseconds.
 */
fun runSerialProcessing(nanoDelay: Long): Long {
    return measureNanoTime {
        val step1 = stepSerial(nanoDelay)
        val step2 = stepSerial(step1)
        val step3 = stepSerial(step2)
    }
}

/**
 * Executes the asynchronous processing by performing three steps using coroutines.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The total execution time in nanoseconds.
 */
suspend fun runAsynchronProcessing(nanoDelay: Long): Long {
    return coroutineScope { // Coroutine context is created here
        measureNanoTime {
            val step1 = async { stepAsynchron(nanoDelay) }
            val step2 = async { stepAsynchron(step1.await()) }
            val step3 = async { stepAsynchron(step2.await()) }
            step3.await() // Synchronize all steps
        }
    }
}

/**
 * Executes the thread-based processing by performing three steps in parallel using threads.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The total execution time in nanoseconds.
 */
fun runThreadProcessing(nanoDelay: Long): Long {
    val executor = Executors.newFixedThreadPool(3)
    return measureNanoTime {
        val latch = CountDownLatch(3) // To synchronize 3 threads

        executor.submit { stepThread(nanoDelay, latch) }
        executor.submit { stepThread(nanoDelay, latch) }
        executor.submit { stepThread(nanoDelay, latch) }

        latch.await() // Wait for all threads to complete
        executor.shutdown() // Always shutdown the executor
    }
}

/**
 * The main function to compare the execution times of serial, asynchronous, and thread-based processing.
 * It repeatedly runs each processing method and compares the results, adjusting the delay after each iteration.
 */
fun main() = runBlocking {
    var nanoDelay = 0L
    val stepIncrement = 10_000L
    val threshold = 500_000_000L // 500 ms as a threshold example

    // Warm-up to stabilize the JVM
    repeat(100) {
        runSerialProcessing(nanoDelay)
        runAsynchronProcessing(nanoDelay)
        runThreadProcessing(nanoDelay)
    }

    while (nanoDelay < threshold) {
        val serialTime = runSerialProcessing(nanoDelay)
        val asyncTime = runAsynchronProcessing(nanoDelay)
        val threadTime = runThreadProcessing(nanoDelay)

        println(
            String.format(
                "Serial: %10.2f $TIME_UNIT, Asynchronous: %12.2f $TIME_UNIT, Thread: %10.2f $TIME_UNIT, Delay: %10.2f $TIME_UNIT",
                convertToDesiredUnit(serialTime, TIME_UNIT),
                convertToDesiredUnit(asyncTime, TIME_UNIT),
                convertToDesiredUnit(threadTime, TIME_UNIT),
                convertToDesiredUnit(nanoDelay, TIME_UNIT)
            )
        )

        if (asyncTime < serialTime && threadTime < serialTime) {
            println("Asynchronous and Threads are faster at ${convertToDesiredUnit(nanoDelay, TIME_UNIT)} $TIME_UNIT delay.")
            break
        }

        nanoDelay += stepIncrement
    }
}
