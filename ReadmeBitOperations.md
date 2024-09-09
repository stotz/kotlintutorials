
# [Kotlin Tutorials](Readme.md)

# Bit Operations in Kotlin

## Overview

This tutorial explains how to use bitwise operations and bitmasking in Kotlin. You will learn about fundamental bitwise operators such as AND, OR, XOR, and NOT, as well as bit shifting and bitmask manipulation.

### Table of Contents

1. [Bitwise AND (`&`) and `and`](#bitwise-and)
2. [Bitwise OR (`|`) and `or`](#bitwise-or)
3. [Bitwise XOR (`^`) and `xor`](#bitwise-xor)
4. [Bitwise NOT (`~`) and `inv`](#bitwise-not)
5. [Left Shift (`<<`) and `shl`](#left-shift)
6. [Right Shift (`>>`) and `shr`](#right-shift)
7. [Unsigned Right Shift (`>>>`) and `ushr`](#unsigned-right-shift)
8. [Using Bitmasks](#using-bitmasks)
9. [Setting, Clearing, and Toggling Bits](#setting-clearing-and-toggling-bits)

---

## Bitwise AND

The bitwise AND operation (`&` in Kotlin and Java) compares the corresponding bits of two numbers. If both bits are `1`, the result is `1`. Otherwise, the result is `0`.

- **Use case**: The AND operation is often used to check whether a specific bit is set in a number.

Example:
```kotlin
val a = 0b1101  // 13 in decimal
val b = 0b1011  // 11 in decimal
val result = a and b  // Result: 0b1001 (9 in decimal)
```

---

## Bitwise OR

The bitwise OR operation (`|` in Kotlin and Java) returns `1` if either of the bits being compared is `1`. If both bits are `0`, the result is `0`.

- **Use case**: OR is typically used to set specific bits in a number.

Example:
```kotlin
val result = a or b  // Result: 0b1111 (15 in decimal)
```

---

## Bitwise XOR

The bitwise XOR operation (`^`) returns `1` if the two bits being compared are different. If both bits are the same, the result is `0`.

---

## Bitwise NOT

The bitwise NOT operation (`~` or `inv()` in Kotlin) inverts all bits of a number. This means every `1` becomes `0` and every `0` becomes `1`.

Example:
```kotlin
val result = a.inv()  // Result: -14 in decimal
```

---

## Left Shift

The left shift operator (`<<` or `shl` in Kotlin) shifts all bits in a number to the left by a specified number of positions.

Example:
```kotlin
val result = a shl 2  // Result: 0b110100 (52 in decimal)
```

---

## Right Shift

The right shift operator (`>>` or `shr` in Kotlin) shifts the bits to the right by a specified number of positions.

---

## Unsigned Right Shift

The unsigned right shift operator (`>>>` or `ushr` in Kotlin) shifts the bits to the right without preserving the sign.

---

## Using Bitmasks

A bitmask is a value used to isolate or modify specific bits in a number.

---

## Setting, Clearing, and Toggling Bits

- **Set a bit**: Use OR (`or`) with a mask to set a bit to `1`.
- **Clear a bit**: Use AND (`and`) with the inverse of a mask to set a bit to `0`.
- **Toggle a bit**: Use XOR (`xor`) with a mask to flip the bit's value.

---

### Conclusion

This tutorial demonstrates the essential bitwise operations in Kotlin and shows how to manipulate individual bits using masks.

For more details and the complete source code, refer to the full example in [BitOperations.kt](src/main/kotlin/tutorials/bitoperations/BitOperations.kt).