package tutorials.dependencyinjection

// Method Injection Example
/**
 * Example demonstrating Method Injection in Kotlin.
 *
 * Method Injection is used when a dependency is only required within a specific method.
 * It keeps the dependency usage limited to where it is strictly necessary.
 */
class MethodInjectedService {
    /**
     * Performs an action using the provided dependency.
     * @param dependency the dependency needed to perform the action
     */
    fun performAction(dependency: DependencyInterface) {
        dependency.doSomething()
    }
}