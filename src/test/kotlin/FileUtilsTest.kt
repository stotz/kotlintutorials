package tutorials.files

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.io.File

class FileUtilsTest {

    private val classLoader = Thread.currentThread().contextClassLoader

    @Test
    fun testReadEntireTextFile() {
        // Ressourcen-Dateien (wie in resources/)
        val utf8UnixNoBomContent = FileUtils.readEntireTextFile("testfile_utf8_unix_no_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8UnixNoBomContent.contains("Höheres Leben"))

        // Test für weitere encodings...
    }

    @Test
    fun testConvertFileEncoding() {
        val tempOutputPath1 = "build/tmp/temp_output1.txt" // Erste temporäre Datei für die Konvertierung nach Windows-1252
        val tempOutputPath2 = "build/tmp/temp_output2.txt" // Zweite temporäre Datei für die Rückkonvertierung nach UTF-8

        // Datei für den Test erstellen
        val outputFile1 = File(tempOutputPath1)
        val outputFile2 = File(tempOutputPath2)
        outputFile1.createNewFile()
        outputFile2.createNewFile()

        // Debug-Ausgabe vor der Konvertierung
        println("Vor der Konvertierung: ${File("src/test/resources/testfile_utf8_unix_no_bom.txt").readText(Charsets.UTF_8)}")

        // Convert from UTF-8 (Unix) to Windows-1252
        FileUtils.convertFileEncoding(
            "src/test/resources/testfile_utf8_unix_no_bom.txt",
            tempOutputPath1,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252")
        )

        // Debug-Ausgabe nach der Konvertierung nach Windows-1252
        val windows1252Content = outputFile1.readText(Charset.forName("windows-1252"))
        println("Nach der Konvertierung (Windows-1252): $windows1252Content")
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Convert back from Windows-1252 to UTF-8
        FileUtils.convertFileEncoding(
            tempOutputPath1,
            tempOutputPath2,
            fromEncoding = Charset.forName("windows-1252"),
            toEncoding = Charsets.UTF_8
        )

        // Debug-Ausgabe nach der Rückkonvertierung nach UTF-8
        val utf8Content = outputFile2.readText(Charsets.UTF_8)
        println("Nach der Rückkonvertierung (UTF-8): $utf8Content")
        assertTrue(utf8Content.contains("Höheres Leben"))

        // Clean up temporary output files
        outputFile1.delete()
        outputFile2.delete()
    }

    @Test
    fun testConvertFileEncodingWithBufferedStreams() {
        val tempOutputPath = "build/tmp/temp_output_streams.txt"
        val outputFile = File(tempOutputPath)
        outputFile.createNewFile()

        FileUtils.convertFileEncodingWithBufferedStreams(
            "src/test/resources/testfile_utf8_unix_no_bom.txt",
            tempOutputPath,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252")
        )

        val windows1252Content = outputFile.readText(Charset.forName("windows-1252"))
        assertTrue(windows1252Content.contains("Höheres Leben"))

        outputFile.delete()
    }

    @Test
    fun testDetectFileEncoding() {
        val utf8UnixNoBomEncoding = FileUtils.detectFileEncoding("testfile_utf8_unix_no_bom.txt", classLoader)
        assertEquals("UTF-8", utf8UnixNoBomEncoding)
    }
}
