package tutorials.compareserialandparalleljmh

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*
import kotlin.system.measureNanoTime
import kotlinx.coroutines.*

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
open class BenchmarkProcessing {

    private val executor = Executors.newFixedThreadPool(3)

    /**
     * Performs a busy-wait for the specified number of nanoseconds.
     */
    fun busyWait(nanos: Long) {
        val end = System.nanoTime() + nanos
        while (System.nanoTime() < end) {
            // Busy-wait to simulate precise delay
        }
    }

    /**
     * Runs the serial processing with a busy wait.
     */
    @Benchmark
    fun runSerialProcessing(): Long {
        val nanoDelay = 1000L
        return measureNanoTime {
            stepSerial(nanoDelay)
            stepSerial(nanoDelay + 100)
            stepSerial(nanoDelay + 200)
        }
    }

    /**
     * Runs asynchronous processing with coroutines.
     */
    @Benchmark
    fun runAsynchronousProcessing(): Long = runBlocking {
        val nanoDelay = 1000L
        return@runBlocking measureNanoTime {
            val step1 = async { stepAsynchron(nanoDelay) }
            val step2 = async { stepAsynchron(step1.await()) }
            val step3 = async { stepAsynchron(step2.await()) }
            step3.await()
        }
    }

    /**
     * Runs processing using traditional threads.
     */
    @Benchmark
    fun runThreadProcessing(): Long {
        val latch = CountDownLatch(3)
        val nanoDelay = 1000L
        return measureNanoTime {
            executor.submit { stepThread(nanoDelay, latch) }
            executor.submit { stepThread(nanoDelay + 100, latch) }
            executor.submit { stepThread(nanoDelay + 200, latch) }
            latch.await()
        }
    }

    /**
     * Runs processing using Java Virtual Threads.
     */
    @Benchmark
    fun runVirtualThreadProcessing(): Long {
        val latch = CountDownLatch(3)
        val nanoDelay = 1000L
        return measureNanoTime {
            val virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()
            virtualExecutor.submit { stepThread(nanoDelay, latch) }
            virtualExecutor.submit { stepThread(nanoDelay + 100, latch) }
            virtualExecutor.submit { stepThread(nanoDelay + 200, latch) }
            latch.await()
            virtualExecutor.shutdown()
        }
    }

    // Helper methods
    fun stepSerial(nanoDelay: Long): Long {
        busyWait(nanoDelay + 100)
        return nanoDelay + 100
    }

    suspend fun stepAsynchron(nanoDelay: Long): Long {
        busyWait(nanoDelay + 100)
        return nanoDelay + 100
    }

    fun stepThread(nanoDelay: Long, latch: CountDownLatch): Long {
        busyWait(nanoDelay + 100)
        latch.countDown()
        return nanoDelay + 100
    }
}

fun main() {
    val opt = OptionsBuilder()
        .include("tutorials.compareserialandparalleljmh.benchmarkprocessing")
        .forks(1)
        .build()

    Runner(opt).run()
}
