package ch.typedef.tutorials.bitoperations

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
    }
}
