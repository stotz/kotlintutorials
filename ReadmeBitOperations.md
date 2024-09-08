# Bit Operations in Kotlin

## Overview

This tutorial demonstrates how to use bitwise operations and bitmasking in Kotlin. It covers various operations like AND, OR, XOR, NOT, and how to manipulate bits with masks.

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

## Bitwise AND

The bitwise AND operation compares each bit of two numbers and returns `1` if both bits are `1`.

Example:
```kotlin
val a = 0b1101  // 13 in decimal
val b = 0b1011  // 11 in decimal
val result = a and b  // Result: 0b1001 (9 in decimal)
```

---

For more details and the complete source code, refer to the full example in [BitOperations.kt](src/main/kotlin/tutorials/bitoperations/BitOperations.kt).