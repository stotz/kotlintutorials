package tutorials.dependencyinjection

// Dependency Class
/**
 * A simple class representing a dependency that implements DependencyInterface.
 */
class Dependency : DependencyInterface {
    /**
     * Example method to demonstrate dependency functionality.
     */
    override fun doSomething() {
        println("Dependency is doing something!")
    }
}