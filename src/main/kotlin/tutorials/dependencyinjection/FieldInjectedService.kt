package tutorials.dependencyinjection

// Field Injection Example
/**
 * Example demonstrating Field Injection in Kotlin using a public property.
 *
 * Field Injection is generally not recommended as it can make dependencies
 * less visible and more difficult to manage, making the class harder to test.
 */
class FieldInjectedService {
    /**
     * Directly injected dependency, publicly visible.
     */
    var dependency: Dependency? = null

    /**
     * Performs an action if the dependency is set.
     */
    fun performAction() {
        dependency?.doSomething() ?: throw IllegalStateException("Dependency has not been set!")
    }
}