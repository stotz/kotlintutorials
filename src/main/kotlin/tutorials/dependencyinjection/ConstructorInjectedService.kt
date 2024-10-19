package tutorials.dependencyinjection

// Constructor Injection Example
/**
 * Example demonstrating Constructor Injection in Kotlin.
 *
 * Constructor Injection is a best practice when we have mandatory dependencies.
 * It makes the dependencies immutable and easy to use for testing.
 */
class ConstructorInjectedService(private val dependency: DependencyInterface) {
    /**
     * Performs an action using the provided dependency.
     */
    fun performAction() {
        dependency.doSomething()
    }
}