package tutorials.dependencyinjection

/**
 * Main function to run and demonstrate different types of Dependency Injection in Kotlin.
 */
fun main() {
    println("--- Constructor Injection Example with DependencyA ---")
    val constructorInjectedServiceA = ConstructorInjectedService(DependencyA())
    constructorInjectedServiceA.performAction()

    println("\n--- Constructor Injection Example with DependencyB ---")
    val constructorInjectedServiceB = ConstructorInjectedService(DependencyB())
    constructorInjectedServiceB.performAction()

    println("\n--- Setter Injection Example ---")
    val setterInjectedService = SetterInjectedService()
    setterInjectedService.setDependency(DependencyA())
    setterInjectedService.performAction()

    println("\n--- Method Injection Example ---")
    val methodInjectedService = MethodInjectedService()
    methodInjectedService.performAction(DependencyB())

    println("\n--- Field Injection Example ---")
    val fieldInjectedService = FieldInjectedService()
    fieldInjectedService.dependency = DependencyA()
    fieldInjectedService.performAction()

    println("\n--- Service Locator Example ---")
    val serviceLocatorInjectedService = ServiceLocatorInjectedService()
    serviceLocatorInjectedService.performAction()

    println("\n--- Scoped Injection Example ---")
    val scopedInjectedService = ScopedInjectedService()
    scopedInjectedService.performAction(DependencyB())

    println("\n--- Lazy Injection Example ---")
    val lazyInjectedService = LazyInjectedService()
    lazyInjectedService.performAction()
}