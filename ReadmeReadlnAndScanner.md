
# Kotlin Input Handling: `readln()` and Java `Scanner`

This tutorial covers how to use `readln()` and Java's `Scanner` in Kotlin for standard input handling. It walks through basic and advanced usage, including validation, custom input handling, and error handling. The goal is to demonstrate how to efficiently read input, perform validation, and handle various types of user input.

## Source File

The complete source code for this tutorial can be found in:
- **Source:** `src/main/kotlin/tutorials/readlnandscanner/ReadlnAndScanner.kt`

---

## Table of Contents

1. [Introduction to `readln()` and `Scanner`](#introduction-to-readln-and-scanner)
2. [Basic Input Handling with `readln()`](#basic-input-handling-with-readln)
3. [Nullable Input Handling with `readlnOrNull()`](#nullable-input-handling-with-readlnornull)
4. [Advanced Input Handling with Java's `Scanner`](#advanced-input-handling-with-javas-scanner)
5. [Input Validation Examples](#input-validation-examples)
6. [Enhanced `readln()` with Lambda Validation](#enhanced-readln-with-lambda-validation)
7. [Exception Handling](#exception-handling)
8. [Full Examples and Best Practices](#full-examples-and-best-practices)

---

## 1. Introduction to `readln()` and `Scanner`

### `readln()`
In Kotlin, `readln()` is a convenient function for reading standard input. It reads a line of input as a string.

### Java's `Scanner`
Kotlin is interoperable with Java, so you can use Java’s `Scanner` class for more complex input handling. `Scanner` is helpful when you need to read specific data types (e.g., integers, doubles), process input with custom delimiters, or read tokens sequentially.

---

## 2. Basic Input Handling with `readln()`

For simple cases, `readln()` is the easiest way to read user input:

```kotlin
println("Enter your name:")
val name = readln()
println("Hello, $name!")
```

`readln()` reads the entire line as a string, making it suitable for reading simple inputs.

---

## 3. Nullable Input Handling with `readlnOrNull()`

Kotlin offers the `readlnOrNull()` function for handling nullable input. It returns `null` if no input is provided, which can be handled using `!!` or `try-catch` blocks.

```kotlin
val input: String? = readlnOrNull()  // Could return null
val nonNullInput: String = input!!  // Throws exception if input is null
println("You entered: $nonNullInput")
```

Handling `null` inputs is important when robustness is required in user input.

---

## 4. Advanced Input Handling with Java's `Scanner`

Java's `Scanner` class offers more flexibility for reading input, particularly for specific data types like integers, doubles, or tokens. 

### Example: Reading Multiple Data Types

```kotlin
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    println("Enter two numbers:")
    val num1 = scanner.nextInt()  // Reads an integer
    val num2 = scanner.nextInt()  // Reads another integer
    println("You entered: $num1 and $num2")
    scanner.close()  // Always close the scanner to release resources
}
```

### Custom Delimiters in `Scanner`

You can use `Scanner` with custom delimiters to control how input is split. For example:

```kotlin
val scanner = Scanner("123_456").useDelimiter("_")
println("First number: ${scanner.nextInt()}")  // 123
println("Second number: ${scanner.nextInt()}") // 456
```

This is useful when dealing with specially formatted input.

---

## 5. Input Validation Examples

### Numeric Range Validation

You can easily validate user input using Kotlin's `in` operator for range checks. Here's an example for validating a month between 1 and 12:

```kotlin
println("Enter a month number (1-12):")
val month = readln().toIntOrNull()
if (month in 1..12) {
    println("Valid month: $month")
} else {
    println("Invalid month. Please enter a number between 1 and 12.")
}
```

### String Validation with Regex

Using regular expressions, you can validate that input conforms to a specific format. Here's an example that validates an email address:

```kotlin
val email = enhancedReadln("Enter your email:") { 
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    emailRegex.matches(it)
}
println("Valid email: $email")
```

This ensures the entered email address matches a formal pattern.

---

## 6. Enhanced `readln()` with Lambda Validation

The enhanced `readln()` function allows you to pass a lambda function for custom validation. This can be used to validate input dynamically and in more complex scenarios.

### Function Definition

```kotlin
fun enhancedReadln(prompt: String? = null, validation: (String) -> Boolean): String {
    if (prompt != null) print(prompt)
    var input = readln()
    while (!validation(input)) {
        println("Invalid input, please try again.")
        if (prompt != null) print(prompt)
        input = readln()
    }
    return input
}
```

### Example: Validating Age

```kotlin
val age = enhancedReadln("Enter your age:") { it.toIntOrNull() != null && it.toInt() in 0..120 }
println("Your valid age is: $age")
```

The lambda allows you to define validation logic directly, and the function will keep prompting until the input is valid.

---

## 7. Exception Handling

Handling exceptions is crucial for robust input handling, especially when working with nullable values or converting user input to specific types.

### Example: Handling `NullPointerException`

```kotlin
try {
    val riskyInput = readlnOrNull() ?: throw NullPointerException("Input was null!")
    println("You entered: $riskyInput")
} catch (e: NullPointerException) {
    println("Caught exception: ${e.message}")
}
```

This ensures that the program doesn't crash due to invalid or null input.

---

## 8. Full Examples and Best Practices

### Full Example: Combining Everything

Here’s a full example that demonstrates using `readln()`, `Scanner`, validation, and exception handling:

```kotlin
import java.util.Scanner

object ReadlnAndScanner {

    @JvmStatic
    fun main(args: Array<String>) {
        // Nullable input handling with readlnOrNull() and forcing non-null with !!
        println("Enter a nullable string:")
        val nullableInput: String? = readlnOrNull()  
        val nonNullInput: String = nullableInput!!   
        println("Non-null input: $nonNullInput")

        // Validating a number between 1-12 (for months)
        println("Enter a month number (1-12):")
        val month = readln().toIntOrNull()
        if (month in 1..12) {
            println("Valid month: $month")
        } else {
            println("Invalid month. Please enter a number between 1 and 12.")
        }

        // Using Scanner for multiple types of input
        val scanner = Scanner(System.`in`)
        println("Enter two integers:")
        try {
            val num1 = scanner.nextInt()
            val num2 = scanner.nextInt()
            println("You entered: $num1 and $num2")
        } catch (e: Exception) {
            println("Invalid input, please enter valid integers.")
        } finally {
            scanner.close()
        }

        // Enhanced readln with custom validation
        val age = enhancedReadln("Enter your age:") { it.toIntOrNull() != null && it.toInt() in 0..120 }
        println("Your valid age is: $age")

        // Regex validation example for email
        val email = enhancedReadln("Enter your email:") { 
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            emailRegex.matches(it)
        }
        println("Valid email: $email")
    }

    // Enhanced readln function with optional prompt and custom validation
    fun enhancedReadln(prompt: String? = null, validation: (String) -> Boolean): String {
        if (prompt != null) print(prompt)
        var input = readln()
        while (!validation(input)) {
            println("Invalid input, please try again.")
            if (prompt != null) print(prompt)
            input = readln()
        }
        return input
    }
}
```

---

For more details and the complete source code, refer to the full example in [ReadlnAndScanner.kt](src/main/kotlin/tutorials/readlnandscanner/ReadlnAndScanner.kt).
