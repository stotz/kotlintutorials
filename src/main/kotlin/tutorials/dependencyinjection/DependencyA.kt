package tutorials.dependencyinjection

// DependencyA Class
/**
 * A class representing DependencyA that implements DependencyInterface.
 */
class DependencyA : DependencyInterface {
    /**
     * Example method to demonstrate dependency functionality.
     */
    override fun doSomething() {
        println("DependencyA is doing something!")
    }
}