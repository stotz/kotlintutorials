package tutorials.dependencyinjection

// Scoped Injection Example
/**
 * Example demonstrating Scoped Injection in Kotlin.
 *
 * Scoped Injection is used to control the lifecycle of dependencies, particularly in web applications.
 */
class ScopedInjectedService {
    /**
     * Dependency instance scoped to a request or other lifecycle scope.
     */
    fun performAction(dependency: Dependency) {
        dependency.doSomething()
    }
}