package tutorials.dependencyinjection

// DependencyB Class
/**
 * A class representing DependencyB that implements DependencyInterface.
 */
class DependencyB : DependencyInterface {
    /**
     * Example method to demonstrate dependency functionality.
     */
    override fun doSomething() {
        println("DependencyB is doing something!")
    }
}