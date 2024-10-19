package tutorials.dependencyinjection

// Filename: SetterInjectedService.kt
// Property Injection (Setter) Example
/**
 * Example demonstrating Property (Setter) Injection in Kotlin.
 *
 * Property Injection is useful for optional dependencies or when dependencies
 * need to be set dynamically. However, care must be taken as the service could
 * be in an invalid state if the dependency is not set properly.
 */
class SetterInjectedService {
    lateinit var dependency: Dependency

    /**
     * Sets the dependency for this service.
     * @param dep the dependency to be set
     */
    fun setDependency(dep: Dependency) {
        this.dependency = dep
    }

    /**
     * Performs an action using the set dependency.
     * Throws an exception if dependency is not set.
     */
    fun performAction() {
        if (::dependency.isInitialized) {
            dependency.doSomething()
        } else {
            throw IllegalStateException("Dependency has not been set!")
        }
    }
}