package tutorials.dependencyinjection

// Filename: LazyInjectedService.kt
// Lazy Injection Example
/**
 * Example demonstrating Lazy Injection in Kotlin.
 *
 * Lazy Injection is used to instantiate dependencies only when needed, optimizing resource usage.
 */
class LazyInjectedService {
    private val dependency: Dependency by lazy { Dependency() }

    /**
     * Performs an action using the lazily initialized dependency.
     */
    fun performAction() {
        dependency.doSomething()
    }
}