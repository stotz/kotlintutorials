package tutorials.compareserialandparallelprecisedelay

import java.util.concurrent.Executors
import java.util.concurrent.CountDownLatch
import kotlin.system.measureNanoTime
import kotlinx.coroutines.*

/**
 * Performs a busy-wait for the specified number of nanoseconds. This simulates a precise delay by continuously
 * checking the current time and waiting until the specified time has passed.
 *
 * @param nanos The number of nanoseconds to busy-wait.
 */
fun busyWait(nanos: Long) {
    val end = System.nanoTime() + nanos
    while (System.nanoTime() < end) {
        // Busy-wait to simulate precise delay
    }
}

/**
 * Simulates a step in the serial processing flow with a delay.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The adjusted delay after performing the step.
 */
fun stepSerial(nanoDelay: Long): Long {
    busyWait(nanoDelay + 100)
    return nanoDelay + 100
}

/**
 * Simulates a step in the asynchronous processing flow with a delay.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The adjusted delay after performing the step.
 */
suspend fun stepAsynchron(nanoDelay: Long): Long {
    busyWait(nanoDelay + 100)
    return nanoDelay + 100
}

/**
 * Simulates a step in the thread-based processing flow with a delay.
 * The function also decrements the CountDownLatch to signal that the step is complete.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @param latch The CountDownLatch used to synchronize the threads.
 * @return The adjusted delay after performing the step.
 */
fun stepThread(nanoDelay: Long, latch: CountDownLatch): Long {
    busyWait(nanoDelay + 100)
    latch.countDown()
    return nanoDelay + 100
}

/**
 * Executes the serial processing flow by calling the serial step function three times sequentially.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The time taken to execute the entire serial processing flow in nanoseconds.
 */
fun runSerialProcessing(nanoDelay: Long): Long {
    return measureNanoTime {
        val step1 = stepSerial(nanoDelay)
        val step2 = stepSerial(step1)
        val step3 = stepSerial(step2)
    }
}

/**
 * Executes the asynchronous processing flow by calling the asynchronous step function three times sequentially.
 * Each step is executed within a coroutine.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The time taken to execute the entire asynchronous processing flow in nanoseconds.
 */
suspend fun runAsynchronProcessing(nanoDelay: Long): Long {
    return coroutineScope {
        measureNanoTime {
            val step1 = async { stepAsynchron(nanoDelay) }
            val step2 = async { stepAsynchron(step1.await()) }
            val step3 = async { stepAsynchron(step2.await()) }
            step3.await()
        }
    }
}

/**
 * Executes the thread-based processing flow by calling the thread-based step function three times in parallel.
 * The function uses a CountDownLatch to synchronize the threads.
 *
 * @param nanoDelay The initial delay in nanoseconds.
 * @return The time taken to execute the entire thread-based processing flow in nanoseconds.
 */
fun runThreadProcessing(nanoDelay: Long): Long {
    val latch = CountDownLatch(3)
    val executor = Executors.newFixedThreadPool(3)
    return measureNanoTime {
        executor.submit { stepThread(nanoDelay, latch) }
        executor.submit { stepThread(nanoDelay, latch) }
        executor.submit { stepThread(nanoDelay, latch) }
        latch.await()
        executor.shutdown()
    }
}

/**
 * Repeatedly executes a non-suspending operation for a given number of iterations and returns the average time.
 *
 * @param iterations The number of iterations to perform.
 * @param nanoDelay The initial delay in nanoseconds for each iteration.
 * @param operation The operation to be executed in each iteration.
 * @return The average time taken to execute the operation in nanoseconds.
 */
fun runWithMultipleIterations(
    iterations: Int,
    nanoDelay: Long,
    operation: () -> Long
): Double {
    var totalTime = 0L
    repeat(iterations) {
        totalTime += operation()
    }
    return totalTime.toDouble() / iterations
}

/**
 * Repeatedly executes a suspending operation for a given number of iterations and returns the average time.
 *
 * @param iterations The number of iterations to perform.
 * @param nanoDelay The initial delay in nanoseconds for each iteration.
 * @param operation The suspending operation to be executed in each iteration.
 * @return The average time taken to execute the operation in nanoseconds.
 */
suspend fun runWithMultipleIterationsSuspend(
    iterations: Int,
    nanoDelay: Long,
    operation: suspend () -> Long
): Double {
    var totalTime = 0L
    repeat(iterations) {
        totalTime += operation()
    }
    return totalTime.toDouble() / iterations
}

/**
 * Main function to compare the performance of serial, asynchronous, and thread-based processing flows.
 * It runs each processing flow for multiple iterations, increasing the delay with each step, and prints the results.
 */
fun main() = runBlocking {
    val iterations = 100
    var nanoDelay = 1000L
    val stepIncrement = 1000L
    val threshold = 500_000_000L // Maximum delay threshold

    while (nanoDelay < threshold) {
        val serialTime = runWithMultipleIterations(iterations, nanoDelay) { runSerialProcessing(nanoDelay) }
        val asyncTime = runWithMultipleIterationsSuspend(iterations, nanoDelay) { runAsynchronProcessing(nanoDelay) }
        val threadTime = runWithMultipleIterations(iterations, nanoDelay) { runThreadProcessing(nanoDelay) }

        println(
            "Serial: %7.2f µs, Asynchronous: %7.2f µs, Thread: %7.2f µs, Delay: %7.2f µs".format(
                serialTime / 1_000.0,
                asyncTime / 1_000.0,
                threadTime / 1_000.0,
                nanoDelay / 1_000.0
            )
        )

        if (asyncTime < serialTime && threadTime < serialTime) {
            println("Asynchronous and Threads are faster at %.2f µs delay.".format(nanoDelay / 1_000.0))
            break
        }

        nanoDelay += stepIncrement
    }
}