package tutorials.files

import java.io.IOException

/**
 * A simple Kotlin program that demonstrates the usage of ClassLoaders to load resources.
 * It explains the difference between `Main::class.java.classLoader` and `Thread.currentThread().contextClassLoader`.
 */
fun main() {
    // Obtain the ClassLoader using the current thread's context ClassLoader.
    // This can be useful in multithreaded environments where different threads may have different ClassLoaders.
    val classLoader = Thread.currentThread().contextClassLoader
    val resource = classLoader.getResource("testfile.txt")

    /**
     * If the resource is found, it attempts to read and print each line in the file.
     * If an error occurs during file reading, it is caught and handled.
     */
    resource?.let {
        runCatching {
            it.openStream().bufferedReader().use { reader ->
                reader.lines().forEach { line -> println(line) }
            }
        }.onFailure { exception ->
            if (exception is IOException) {
                println("Error while reading the file: ${exception.message}")
            } else {
                println("An unexpected error occurred: ${exception.message}")
            }
        }
    } ?: println("File not found.")
}

/**
 * # ClassLoader Explanation:
 *
 * ## 1. `Main::class.java.classLoader`
 * This retrieves the ClassLoader associated with the `Main` class (or any other class specified).
 * It is tightly coupled to the class itself and is useful when loading resources directly related to that class.
 *
 * ### Advantages:
 * - **Stability and Predictability:** The ClassLoader of the class is always consistent, regardless of the thread executing the code.
 * - **Class-Coupled Resources:** If you're loading a resource directly tied to the class (e.g., a resource in the same package), this is the best approach.
 *
 * ### Example:
 * ```kotlin
 * val classLoader = Main::class.java.classLoader
 * val resource = classLoader.getResource("testfile.txt")
 * ```
 * This ensures that the ClassLoader of `Main` is used to load the resource.
 *
 * ## 2. `Thread.currentThread().contextClassLoader`
 * This retrieves the ClassLoader associated with the current thread.
 * It is more flexible and commonly used in multithreaded applications or enterprise environments where different threads might have different ClassLoaders.
 *
 * ### Advantages:
 * - **Thread-Specific ClassLoader:** Especially useful in environments where different threads have different ClassLoaders (e.g., application servers).
 * - **Framework Support:** Many frameworks dynamically set the `contextClassLoader` for threads, allowing resources to be loaded appropriately depending on the thread's execution context.
 *
 * ### Example:
 * ```kotlin
 * val classLoader = Thread.currentThread().contextClassLoader
 * val resource = classLoader.getResource("testfile.txt")
 * ```
 * This approach allows you to take advantage of any dynamically set ClassLoaders in multithreaded environments.
 *
 * ## When to Use `Main::class.java.classLoader`:
 * - In simpler applications or when the resource is tightly bound to the class.
 * - When the application is single-threaded or when the ClassLoader context doesn't change across threads.
 *
 * ## When to Use `Thread.currentThread().contextClassLoader`:
 * - In multithreaded environments where different threads may have different ClassLoaders.
 * - In complex applications or frameworks (e.g., web or enterprise servers) that dynamically adjust ClassLoader contexts for different threads.
 * - To ensure compatibility with systems where thread-specific ClassLoaders are set to handle specific resources.
 *
 * ## Conclusion:
 * - Use `Main::class.java.classLoader` when you want to load a resource that is closely tied to a specific class and when thread ClassLoader contexts are not a concern.
 * - Use `Thread.currentThread().contextClassLoader` in more dynamic, multithreaded, or complex environments where the ClassLoader might differ depending on the execution context.
 */
