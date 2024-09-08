package ch.typedef.tutorials.readlnandscanner

import java.util.Scanner

object ReadlnAndScanner {

    /**
     * Main function demonstrating the usage of readln() and Scanner
     * @param args: Pass example numbers as arguments to run specific examples.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            when (args[0]) {
                "1" -> runExample1()
                "2" -> runExample2()
                "3" -> runExample3()
                "4" -> runExample4()
                "5" -> runExample5()
                "6" -> runExample6()
                "7" -> runExample7()
                "8" -> runExample8()
                "9" -> runExample9()
                "10" -> runExample10()
                else -> println("Invalid argument. Please enter a number between 1 and 10.")
            }
        } else {
            println("No argument provided. Running all examples sequentially.")
            runExample1()
            runExample2()
            runExample3()
            runExample4()
            runExample5()
            runExample6()
            runExample7()
            runExample8()
            runExample9()
            runExample10()
        }
    }

    // Example 1: Nullable input handling with readlnOrNull() and forcing non-null with !!
    fun runExample1() {
        try {
            println("Example 1: Enter a nullable string:")
            val nullableInput: String? = readlnOrNull()
            val nonNullInput: String = nullableInput!!
            println("Non-null input: $nonNullInput")
        } catch (e: NullPointerException) {
            println("Caught exception: Input was null!")
        }
    }

    // Example 2: Validating a number between 1-12 (for months)
    fun runExample2() {
        println("Example 2: Enter a month number (1-12):")
        val month = readln().toIntOrNull()
        if (month in 1..12) {
            println("Valid month: $month")
        } else {
            println("Invalid month. Please enter a number between 1 and 12.")
        }
    }

    // Example 3: Using Scanner for multiple types of input without closing the stream
    fun runExample3() {
        val scanner = Scanner(System.`in`)
        println("Example 3: Enter two integers:")
        try {
            val num1 = scanner.nextInt()
            val num2 = scanner.nextInt()
            println("You entered: $num1 and $num2")
        } catch (e: Exception) {
            println("Invalid input, please enter valid integers.")
        }
        // Do not close the scanner to avoid closing the input stream (System.in)
    }

    // Example 4: Custom delimiter in Scanner
    fun runExample4() {
        val customScanner = Scanner("123_456").useDelimiter("_")
        try {
            println("Example 4: Using custom delimiter:")
            println("First number: ${customScanner.nextInt()}")
            println("Second number: ${customScanner.nextInt()}")
        } catch (e: Exception) {
            println("Error reading numbers.")
        }
        // Closing the customScanner here is safe since it's not linked to System.in
        customScanner.close()
    }

    // Example 5: Enhanced readln() function with optional prompt and custom validation
    fun runExample5() {
        val age = enhancedReadln("Example 5: Enter your age:") { it.toIntOrNull() != null && it.toInt() in 0..120 }
        println("Your valid age is: $age")
    }

    // Example 6: Regex validation using readln()
    fun runExample6() {
        println("Example 6: Enter a string (only letters and spaces allowed):")
        val strInput = readln()
        val regex = Regex("^[A-Za-z ]+$")
        if (regex.matches(strInput)) {
            println("Valid string!")
        } else {
            println("Invalid string. Only letters and spaces are allowed.")
        }
    }

    // Example 7: Try-catch handling with readln() and nullability
    fun runExample7() {
        try {
            println("Example 7: Enter a nullable string:")
            val riskyInput = readlnOrNull() ?: throw NullPointerException("Input was null!")
            println("You entered: $riskyInput")
        } catch (e: NullPointerException) {
            println("Caught exception: ${e.message}")
        }
    }

    // Example 8: Using Scanner's hasNext() to check for more input
    fun runExample8() {
        val wordScanner = Scanner("Hello Kotlin!")
        println("Example 8: Reading words from a string:")
        while (wordScanner.hasNext()) {
            println(wordScanner.next())
        }
        // Safe to close custom wordScanner, not linked to System.in
        wordScanner.close()
    }

    // Example 9: Using enhancedReadln with custom validation via lambda (email format)
    fun runExample9() {
        val email = enhancedReadln("Example 9: Enter your email:") {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            emailRegex.matches(it)
        }
        println("Valid email: $email")
    }

    // Example 10: One-liner with readln(), !!, and exception handling
    fun runExample10() {
        println("Example 10: Enter a number:")
        val number: Int = try {
            readln().toInt()
        } catch (e: Exception) {
            println("Invalid input")
            return
        }
        println("You entered: $number")
    }

    /**
     * Enhanced readln function with an optional prompt and custom validation through a lambda
     * @param prompt the message to display to the user before input
     * @param validation a lambda function to validate the input
     * @return the valid input if validation succeeds
     */
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
