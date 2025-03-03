# Kotlin-Tutorial: Gruppierung und Aggregation von Listen und Maps

## Einführung

Kotlin bietet leistungsstarke Funktionen zur Gruppierung und Aggregation von Elementen in Listen und Maps. Dazu gehören:
- `groupBy()`
- `groupingBy()`
- `fold()`
- `aggregate()`
- `reduce()`
- `eachCount()`
- `reduceOrNull()`

In diesem Tutorial behandeln wir jede dieser Funktionen mit fünf vollständigen, praxisnahen Beispielen.

---
## Die Funktion `groupBy()`

### Beispiel 1: Gruppierung nach Anfangsbuchstaben
```kotlin
fun main() {
    val names = listOf("Alice", "Anna", "Bob", "Bella", "Charlie")
    val grouped = names.groupBy { it.first() }
    println(grouped) // {A=[Alice, Anna], B=[Bob, Bella], C=[Charlie]}
}
```

### Beispiel 2: Gruppierung von Zahlen nach Gerade/Ungerade
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val grouped = numbers.groupBy { it % 2 == 0 }
    println(grouped) // {false=[1, 3, 5], true=[2, 4, 6]}
}
```

### Beispiel 3: Gruppierung nach Zeichenlänge
```kotlin
fun main() {
    val words = listOf("car", "train", "bus", "bike")
    val grouped = words.groupBy { it.length }
    println(grouped) // {3=[car, bus], 5=[train], 4=[bike]}
}
```

### Beispiel 4: Gruppierung nach Vokal oder Konsonant
```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date", "eggplant")
    val grouped = words.groupBy { it.first() in listOf('a', 'e', 'i', 'o', 'u') }
    println(grouped) // {true=[apple, eggplant], false=[banana, cherry, date]}
}
```

### Beispiel 5: Gruppierung von Studenten nach Notenbereich
```kotlin
fun main() {
    val students = listOf("Alice" to 85, "Bob" to 92, "Charlie" to 78, "David" to 88)
    val grouped = students.groupBy { when (it.second) {
        in 90..100 -> "Sehr gut"
        in 80..89 -> "Gut"
        else -> "Befriedigend"
    } }
    println(grouped) // {Gut=[(Alice, 85), (David, 88)], Sehr gut=[(Bob, 92)], Befriedigend=[(Charlie, 78)]}
}
```

---
## Die Funktion `groupingBy()`

### Beispiel 1: Häufigkeit von Anfangsbuchstaben zählen
```kotlin
fun main() {
    val names = listOf("Alice", "Anna", "Bob", "Bella", "Charlie")
    val counts = names.groupingBy { it.first() }.eachCount()
    println(counts) // {A=2, B=2, C=1}
}
```

### Beispiel 2: Zeichenlängen summieren
```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date")
    val lengthSum = words.groupingBy { it.first() }.fold(0) { acc, word -> acc + word.length }
    println(lengthSum) // {a=5, b=6, c=6, d=4}
}
```

### Beispiel 3: Längstes Wort pro Anfangsbuchstabe
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry")
    val longest = words.groupingBy { it.first() }.reduce { _, acc, word -> if (word.length > acc.length) word else acc }
    println(longest) // {a=apricot, b=blueberry}
}
```

### Beispiel 4: Anzahl von Zahlen in Gruppen zählen
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    val countByGroup = numbers.groupingBy { it % 3 }.eachCount()
    println(countByGroup) // {1=3, 2=3, 0=2}
}
```

### Beispiel 5: Gruppierung und Aggregation mit `fold`
```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date", "apricot")
    val wordLengths = words.groupingBy { it.first() }.fold(0) { acc, word -> acc + word.length }
    println(wordLengths) // {a=12, b=6, c=6, d=4}
}
```

---
## Die Funktion `fold()`

### Beispiel 1: Summieren einer Liste von Zahlen
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.fold(0) { acc, num -> acc + num }
    println(sum) // 15
}
```

### Beispiel 2: Verkettung einer Liste von Strings
```kotlin
fun main() {
    val words = listOf("Hello", "World", "Kotlin")
    val sentence = words.fold("Start:") { acc, word -> "$acc $word" }
    println(sentence) // Start: Hello World Kotlin
}
```

### Beispiel 3: Finden des längsten Strings in einer Liste
```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date")
    val longestWord = words.fold("") { acc, word -> if (word.length > acc.length) word else acc }
    println(longestWord) // banana
}
```

### Beispiel 4: Berechnung der Produktwerte einer Liste
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val product = numbers.fold(1) { acc, num -> acc * num }
    println(product) // 120
}
```

### Beispiel 5: Umkehren einer Zeichenkette
```kotlin
fun main() {
    val text = "Kotlin"
    val reversed = text.fold("") { acc, char -> char + acc }
    println(reversed) // ntiloK
}
```

---
## Die Funktion `aggregate()`

### Beispiel 1: Anzahl der Elemente in jeder Gruppe
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry")
    val wordCounts = words.groupingBy { it.first() }
        .aggregate { _, accumulator: Int?, _, first -> if (first) 1 else accumulator!! + 1 }
    println(wordCounts) // {a=2, b=2, c=1}
}
```

### Beispiel 2: Berechnung der maximalen Länge pro Gruppe
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry")
    val maxLength = words.groupingBy { it.first() }
        .aggregate { _, accumulator: Int?, word, first -> if (first) word.length else maxOf(accumulator!!, word.length) }
    println(maxLength) // {a=7, b=9, c=6}
}
```

### Beispiel 3: Summierung der Zeichenlängen pro Gruppe
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry")
    val totalLength = words.groupingBy { it.first() }
        .aggregate { _, accumulator: Int?, word, first -> if (first) word.length else accumulator!! + word.length }
    println(totalLength) // {a=12, b=15, c=6}
}
```

### Beispiel 4: Finden des kürzesten Wortes pro Gruppe
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry")
    val shortestWord = words.groupingBy { it.first() }
        .aggregate { _, accumulator: String?, word, first -> if (first) word else if (word.length < accumulator!!.length) word else accumulator }
    println(shortestWord) // {a=apple, b=banana, c=cherry}
}
```

### Beispiel 5: Prüfen, ob eine Gruppe nur Wörter mit gerader Länge enthält
```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry")
    val evenLengthOnly = words.groupingBy { it.first() }
        .aggregate { _, accumulator: Boolean?, word, first -> if (first) word.length % 2 == 0 else accumulator!! && word.length % 2 == 0 }
    println(evenLengthOnly) // {a=false, b=false, c=true}
}
```

---
## Die Funktion `reduce()`

### Beispiel 1: Summierung einer Liste von Zahlen
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.reduce { acc, num -> acc + num }
    println(sum) // 15
}
```

### Beispiel 2: Verkettung einer Liste von Strings
```kotlin
fun main() {
    val words = listOf("Hello", "World", "Kotlin")
    val sentence = words.reduce { acc, word -> "$acc $word" }
    println(sentence) // Hello World Kotlin
}
```

### Beispiel 3: Finden des größten Werts in einer Liste
```kotlin
fun main() {
    val numbers = listOf(3, 8, 2, 7, 5)
    val max = numbers.reduce { acc, num -> if (num > acc) num else acc }
    println(max) // 8
}
```

### Beispiel 4: Multiplizieren aller Zahlen in einer Liste
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val product = numbers.reduce { acc, num -> acc * num }
    println(product) // 120
}
```

### Beispiel 5: Umkehren einer Zeichenkette
```kotlin
fun main() {
    val text = "Kotlin"
    val reversed = text.toList().reduce { acc, char -> char + acc }
    println(reversed) // ntiloK
}
```

---
## Die Funktion `eachCount()`

### Beispiel 1: Häufigkeit von Anfangsbuchstaben zählen
```kotlin
fun main() {
    val names = listOf("Alice", "Anna", "Bob", "Bella", "Charlie")
    val counts = names.groupingBy { it.first() }.eachCount()
    println(counts) // {A=2, B=2, C=1}
}
```

### Beispiel 2: Zählen der Vorkommen von Wörtern
```kotlin
fun main() {
    val words = listOf("apple", "banana", "apple", "cherry", "banana", "banana")
    val wordCounts = words.groupingBy { it }.eachCount()
    println(wordCounts) // {apple=2, banana=3, cherry=1}
}
```

### Beispiel 3: Häufigkeit von Zahlenwerten bestimmen
```kotlin
fun main() {
    val numbers = listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
    val numberCounts = numbers.groupingBy { it }.eachCount()
    println(numberCounts) // {1=1, 2=2, 3=3, 4=4}
}
```

### Beispiel 4: Zählen der Zeichen in einem Text
```kotlin
fun main() {
    val text = "Kotlin ist toll!"
    val charCounts = text.filter { it.isLetter() }.groupingBy { it }.eachCount()
    println(charCounts) // {K=1, o=2, t=3, l=3, i=2, n=1, s=1}
}
```

### Beispiel 5: Häufigkeit von Wortlängen bestimmen
```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date", "eggplant")
    val lengthCounts = words.groupingBy { it.length }.eachCount()
    println(lengthCounts) // {5=2, 6=1, 7=2}
}
```

---
## Die Funktion `reduceOrNull()`

### Beispiel 1: Summierung einer Liste von Zahlen
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.reduceOrNull { acc, num -> acc + num }
    println(sum) // 15
}
```

### Beispiel 2: Verkettung einer Liste von Strings
```kotlin
fun main() {
    val words = listOf("Hello", "World", "Kotlin")
    val sentence = words.reduceOrNull { acc, word -> "$acc $word" }
    println(sentence) // Hello World Kotlin
}
```

### Beispiel 3: Finden des größten Werts in einer Liste
```kotlin
fun main() {
    val numbers = listOf(3, 8, 2, 7, 5)
    val max = numbers.reduceOrNull { acc, num -> if (num > acc) num else acc }
    println(max) // 8
}
```

### Beispiel 4: Multiplizieren aller Zahlen in einer Liste
```kotlin
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val product = numbers.reduceOrNull { acc, num -> acc * num }
    println(product) // 120
}
```

### Beispiel 5: Umkehren einer Zeichenkette
```kotlin
fun main() {
    val text = "Kotlin"
    val reversed = text.toList().reduceOrNull { acc, char -> char + acc }
    println(reversed) // ntiloK
}
```

---
## Fazit
Dieses Tutorial zeigt, wie `groupBy()`, `groupingBy()`, `fold()`, `aggregate()`, `reduce()`, `eachCount()` und `reduceOrNull()` effizient eingesetzt werden können. Diese Funktionen ermöglichen eine strukturierte Analyse und Verarbeitung von Daten in Kotlin.
