package ch.typedef.tutorials.bitoperations

fun <T> toBinaryString(value: T): String {
    return when (value) {
        is Byte -> value.toString(2).padStart(8, '0')
        is UByte -> value.toString(2).padStart(8, '0')
        is Short -> value.toString(2).padStart(16, '0')
        is UShort -> value.toString(2).padStart(16, '0')
        is Int -> value.toString(2).padStart(32, '0')
        is UInt -> value.toString(2).padStart(32, '0')
        is Long -> value.toString(2).padStart(64, '0')
        is ULong -> value.toString(2).padStart(64, '0')
        is Float -> {
            val bits = java.lang.Float.floatToIntBits(value)
            bits.toString(2).padStart(32, '0')
        }
        is Double -> {
            val bits = java.lang.Double.doubleToLongBits(value)
            bits.toString(2).padStart(64, '0')
        }
        is Boolean -> if (value) "1" else "0"
        is Char -> value.code.toString(2).padStart(16, '0') // Unicode value of Char
        is String -> value.map { it.code.toString(2).padStart(16, '0') }.joinToString(" ") // Binary of each character
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

object BitOperations {
    @JvmStatic
    fun main(args: Array<String>) {
        // Example 1: Bitwise AND (&) and Kotlin 'and'
        val a = 0b1101  // Binary for 13
        val b = 0b1011  // Binary for 11
        val resultAnd = a and b
        println("a & b = $resultAnd (Binary: ${resultAnd.toString(2)})")  // Result: 9 (1001)

        // Example 2: Bitwise OR (|) and Kotlin 'or'
        val resultOr = a or b
        println("a | b = $resultOr (Binary: ${resultOr.toString(2)})")  // Result: 15 (1111)

        // Example 3: Bitwise XOR (^) and Kotlin 'xor'
        val resultXor = a xor b
        println("a ^ b = $resultXor (Binary: ${resultXor.toString(2)})")  // Result: 6 (0110)

        // Example 4: Bitwise NOT (~) and Kotlin 'inv'
        val resultNotA = a.inv()
        println("~a = $resultNotA (Binary: ${resultNotA.toString(2)})")

        // Example 5: Left Shift (<<)
        val leftShift = a shl 2
        println("a << 2 = $leftShift (Binary: ${leftShift.toString(2)})")  // Result: 52 (110100)

        // Example 6: Right Shift (>>)
        val rightShift = a shr 2
        println("a >> 2 = $rightShift (Binary: ${rightShift.toString(2)})")  // Result: 3 (11)

        // Example 7: Unsigned Right Shift (>>>)
        val unsignedRightShift = a ushr 2
        println("a >>> 2 = $unsignedRightShift (Binary: ${unsignedRightShift.toString(2)})")  // Result: 3 (11)

        // Example 8: Using Bitmask to check specific bits
        val mask = 0b0100  // Mask for checking the 3rd bit (value 4)
        val isThirdBitSet = (a and mask) != 0
        println("Is the 3rd bit set in a? $isThirdBitSet")  // True if 3rd bit is 1

        // Example 9: Setting a bit with a mask
        val setBit = a or mask
        println("Setting 3rd bit in a: $setBit (Binary: ${setBit.toString(2)})")

        // Example 10: Clearing a bit with a mask
        val clearBit = a and mask.inv()
        println("Clearing 3rd bit in a: $clearBit (Binary: ${clearBit.toString(2)})")

        // Example 11: Toggling a bit with XOR
        val toggleBit = a xor mask
        println("Toggling 3rd bit in a: $toggleBit (Binary: ${toggleBit.toString(2)})")

        val byteValue: Byte = 5
        val shortValue: Short = 123
        val intValue: Int = 123456
        val longValue: Long = 123456789L
        val floatValue: Float = 12.34f
        val doubleValue: Double = 12.34
        val booleanValue: Boolean = true
        val charValue: Char = 'A'
        val stringValue: String = "Hi"

        println("Byte (5): ${toBinaryString(byteValue)}")
        println("Short (123): ${toBinaryString(shortValue)}")
        println("Int (123456): ${toBinaryString(intValue)}")
        println("Long (123456789): ${toBinaryString(longValue)}")
        println("Float (12.34): ${toBinaryString(floatValue)}")
        println("Double (12.34): ${toBinaryString(doubleValue)}")
        println("Boolean (true): ${toBinaryString(booleanValue)}")
        println("Char ('☺'): ${toBinaryString(charValue)}")
        println("String ('Hi ☺'): ${toBinaryString(stringValue)}")
    }
}
