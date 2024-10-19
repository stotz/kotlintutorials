package tutorials.dependencyinjection

// Service Locator Example
/**
 * Example demonstrating the use of a Service Locator pattern in Kotlin.
 *
 * The Service Locator pattern is sometimes used for flexible dependency retrieval,
 * but it is generally discouraged in favor of pure dependency injection techniques.
 */
object ServiceLocator {
    val dependency: DependencyInterface = Dependency()
}

class ServiceLocatorInjectedService {
    /**
     * Performs an action using a dependency retrieved from the Service Locator.
     */
    fun performAction() {
        val dependency = ServiceLocator.dependency
        dependency.doSomething()
    }
}