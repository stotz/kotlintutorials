# Kotlin-Tutorial: Gruppierung von Listen und Maps

## Einführung

In Kotlin gibt es verschiedene Möglichkeiten, Elemente innerhalb von Listen und Maps sinnvoll zu gruppieren. Die Gruppierung ermöglicht es, Elemente nach bestimmten Kriterien zusammenzufassen und anschließend weiterzuverarbeiten. Kotlin bietet dafür leistungsfähige Funktionen wie `groupBy()`, `groupingBy()`, `fold()`, `reduce()` und `aggregate()`. In diesem Tutorial lernen wir, wie man diese Methoden gezielt einsetzen kann.

## Die Funktion `groupBy()`

Die `groupBy()`-Funktion gruppiert Elemente anhand eines definierten Schlüssels und gibt eine `Map` zurück, bei der die Schlüssel den Gruppen entsprechen und die Werte Listen der zugehörigen Elemente enthalten.

### Beispiel: Gruppierung nach Name-Länge

```kotlin
fun main() {
    val words = listOf("apple", "banana", "cherry", "date", "fig", "grape")
    
    val groupedByLength = words.groupBy { it.length }
    println(groupedByLength) // {5=[apple, grape], 6=[banana, cherry], 4=[date, fig]}
}
```

Hier werden die Wörter nach ihrer Länge gruppiert. Das Ergebnis ist eine `Map`, bei der die Schlüssel die Längen der Wörter sind und die Werte Listen der zugehörigen Wörter enthalten.

## Erweiterte Gruppierung mit `groupingBy()`

Während `groupBy()` sofort eine `Map` zurückgibt, erzeugt `groupingBy()` ein `Grouping`-Objekt, das weitere Operationen wie Zählen oder Aggregieren ermöglicht.

### Beispiel: Häufigkeit von Anfangsbuchstaben zählen

```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry", "clementine")
    
    val letterCounts = words.groupingBy { it.first() }.eachCount()
    println(letterCounts) // {a=2, b=2, c=2}
}
```

Hier wird gezählt, wie oft Wörter mit demselben Anfangsbuchstaben auftreten.

## Aggregation mit `fold()` und `reduce()`

Die Methoden `fold()` und `reduce()` ermöglichen es, Elemente innerhalb einer Gruppe weiterzuverarbeiten.

### Beispiel: Summe der Zeichenlängen je Anfangsbuchstabe mit `fold()`

```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry", "clementine")
    
    val totalLengths = words.groupingBy { it.first() }
        .fold(0) { acc, word -> acc + word.length }
    
    println(totalLengths) // {a=12, b=15, c=17}
}
```

Hier wird die Gesamtlänge der Wörter pro Anfangsbuchstabe berechnet.

### Beispiel: Längstes Wort je Gruppe mit `reduce()`

```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry", "clementine")
    
    val longestWordPerLetter = words.groupingBy { it.first() }
        .reduce { _, acc, word -> if (word.length > acc.length) word else acc }
    
    println(longestWordPerLetter) // {a=apricot, b=blueberry, c=clementine}
}
```

Hier wird für jede Gruppe das längste Wort ermittelt.

## Aggregation mit `aggregate()`

`aggregate()` ermöglicht eine flexible Verarbeitung der Gruppen mit individuellen Anfangswerten.

### Beispiel: Anzahl der Wörter je Gruppe mit `aggregate()`

```kotlin
fun main() {
    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry", "clementine")
    
    val groupSizes = words.groupingBy { it.first() }
        .aggregate { _, accumulator: Int?, _, first -> if (first) 1 else accumulator!! + 1 }
    
    println(groupSizes) // {a=2, b=2, c=2}
}
```

Hier wird gezählt, wie viele Wörter es in jeder Gruppe gibt.

## Fazit

Dieses Tutorial hat gezeigt, wie man mit `groupBy()`, `groupingBy()`, `fold()`, `reduce()` und `aggregate()` effizient Gruppierungen in Kotlin durchführen kann. Mit diesen Funktionen lassen sich Daten sinnvoll strukturieren und verarbeiten, um sie für verschiedene Anwendungsfälle nutzbar zu machen. Jetzt bist du dran – probiere es mit deinen eigenen Daten aus!
