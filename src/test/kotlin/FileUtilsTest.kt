package tutorials.files

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.io.File

/**
 * This test class demonstrates various functionalities of the FileUtils class, such as
 * reading text files with different encodings, converting files between encodings,
 * and detecting file encodings.
 *
 * The test suite uses files stored in the `src/test/resources` directory with various encoding formats.
 */
class FileUtilsTest {

    // ClassLoader to load resources from the src/test/resources directory
    private val classLoader = Thread.currentThread().contextClassLoader

    /**
     * Test to verify the functionality of reading entire text files in different encodings.
     * This test checks if the file content is read correctly, particularly in UTF-8 encoding.
     *
     * Files being tested:
     * - UTF-8 without BOM (Byte Order Mark)
     *
     * Assertions:
     * - The file should contain the text "Höheres Leben".
     */
    @Test
    fun testReadEntireTextFile() {
        // Reading a file encoded in UTF-8 (without BOM)
        val utf8UnixNoBomContent = FileUtils.readEntireTextFile("testfile_utf8_unix_no_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8UnixNoBomContent.contains("Höheres Leben"))

        // Additional tests can be added here to check for other encodings (e.g., UTF-16, Windows-1252)
    }

    /**
     * Test to verify the functionality of reading a Big5 encoded text file.
     * This test ensures that the file encoded in Big5 (Traditional Chinese) is read correctly.
     *
     * Files being tested:
     * - Big5 encoded file (testfile_big5_traditionell_windows.txt)
     *
     * Assertions:
     * - The file should contain the expected Traditional Chinese text.
     */
    @Test
    fun testReadBig5File() {
        // Reading a file encoded in Big5 (Traditional Chinese for Windows)
        val big5Content = FileUtils.readEntireTextFile("testfile_big5_traditionell_windows.txt", Charset.forName("Big5"), classLoader)
        assertTrue(big5Content.contains("更高的生活"))

        // Debug output
        println("Big5 file content: $big5Content")
    }

    /**
     * Test to verify the file encoding conversion functionality between UTF-8 and Windows-1252.
     * This test converts a file encoded in UTF-8 to Windows-1252 and then back to UTF-8, ensuring that
     * the content remains intact after the round-trip conversion.
     *
     * Files used:
     * - src/test/resources/testfile_utf8_unix_no_bom.txt (source)
     *
     * Temporary files:
     * - Two temporary files are created in the `build/tmp/` directory for the encoding and decoding processes.
     *
     * Assertions:
     * - The content of the file should contain "Höheres Leben" after each conversion.
     */
    @Test
    fun testConvertFileEncoding() {
        val tempOutputPath1 = "build/tmp/temp_output1.txt" // First temporary file for conversion to Windows-1252
        val tempOutputPath2 = "build/tmp/temp_output2.txt" // Second temporary file for conversion back to UTF-8

        // Create temporary files
        val outputFile1 = File(tempOutputPath1)
        val outputFile2 = File(tempOutputPath2)
        outputFile1.createNewFile()
        outputFile2.createNewFile()

        // Debug output before conversion
        println("Before conversion: ${File("src/test/resources/testfile_utf8_unix_no_bom.txt").readText(Charsets.UTF_8)}")

        // Convert from UTF-8 (Unix) to Windows-1252
        FileUtils.convertFileEncoding(
            "src/test/resources/testfile_utf8_unix_no_bom.txt",
            tempOutputPath1,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252")
        )

        // Debug output after conversion to Windows-1252
        val windows1252Content = outputFile1.readText(Charset.forName("windows-1252"))
        println("After conversion (Windows-1252): $windows1252Content")
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Convert back from Windows-1252 to UTF-8
        FileUtils.convertFileEncoding(
            tempOutputPath1,
            tempOutputPath2,
            fromEncoding = Charset.forName("windows-1252"),
            toEncoding = Charsets.UTF_8
        )

        // Debug output after converting back to UTF-8
        val utf8Content = outputFile2.readText(Charsets.UTF_8)
        println("After converting back (UTF-8): $utf8Content")
        assertTrue(utf8Content.contains("Höheres Leben"))

        // Clean up temporary output files
        outputFile1.delete()
        outputFile2.delete()
    }

    /**
     * Test to verify the file encoding conversion using buffered streams.
     * This approach ensures that larger files can be processed without consuming too much memory.
     * The conversion is done from UTF-8 to Windows-1252 using FileUtils.
     *
     * Assertions:
     * - The content should remain intact after conversion.
     */
    @Test
    fun testConvertFileEncodingWithBufferedStreams() {
        val tempOutputPath = "build/tmp/temp_output_streams.txt" // Temporary file for buffered streams conversion
        val outputFile = File(tempOutputPath)
        outputFile.createNewFile()

        // Convert from UTF-8 to Windows-1252 using buffered streams
        FileUtils.convertFileEncodingWithBufferedStreams(
            "src/test/resources/testfile_utf8_unix_no_bom.txt",
            tempOutputPath,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252")
        )

        val windows1252Content = outputFile.readText(Charset.forName("windows-1252"))
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Clean up temporary output file
        outputFile.delete()
    }

    /**
     * Test to verify the file encoding detection functionality.
     * This method tests whether the correct file encoding can be detected using FileUtils.detectFileEncoding.
     * The file in this test is encoded in UTF-8.
     *
     * Files being tested:
     * - UTF-8 without BOM (testfile_utf8_unix_no_bom.txt)
     *
     * Assertions:
     * - The detected encoding should match "UTF-8".
     */
    @Test
    fun testDetectFileEncoding() {
        // Detect encoding of a file in UTF-8 (without BOM)
        val utf8UnixNoBomEncoding = FileUtils.detectFileEncoding("testfile_utf8_unix_no_bom.txt", classLoader)
        assertEquals("UTF-8", utf8UnixNoBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-16 BE file with BOM.
     */
    @Test
    fun testDetectFileEncodingUtf16BeBom() {
        val utf16BeBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf16_be_bom.txt", classLoader)
        println("Detected encoding for UTF-16 BE with BOM: $utf16BeBomEncoding")
        assertEquals("UTF-16BE", utf16BeBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-16 BE file without BOM.
     */
    @Test
    fun testDetectFileEncodingUtf16BeNoBom() {
        val utf16BeNoBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf16_be_no_bom.txt", classLoader)
        println("Detected encoding for UTF-16 BE without BOM: $utf16BeNoBomEncoding")
        assertTrue(utf16BeNoBomEncoding.equals("UTF-16BE") || utf16BeNoBomEncoding.equals("WINDOWS-1252"))
    }

    /**
     * Tests detecting the encoding of a UTF-16 LE file with BOM.
     */
    @Test
    fun testDetectFileEncodingUtf16LeBom() {
        val utf16LeBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf16_le_bom.txt", classLoader)
        assertEquals("UTF-16LE", utf16LeBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-16 LE file without BOM.
     */
    @Test
    fun testDetectFileEncodingUtf16LeNoBom() {
        val utf16LeNoBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf16_le_no_bom.txt", classLoader)
        assertTrue(utf16LeNoBomEncoding == "UTF-16LE" || utf16LeNoBomEncoding == "WINDOWS-1252")
    }

    /**
     * Tests detecting the encoding of a Windows-1252 file.
     */
    @Test
    fun testDetectFileEncodingWindows1252() {
        val windows1252Encoding = FileUtils.detectFileEncoding("src/test/resources/testfile_windows-1252.txt", classLoader)
        assertEquals("WINDOWS-1252", windows1252Encoding)
    }

    /**
     * Tests detecting the encoding of a Big5 encoded file (Traditional Chinese).
     */
    @Test
    fun testDetectFileEncodingBig5() {
        val big5Encoding = FileUtils.detectFileEncoding("src/test/resources/testfile_big5_traditionell_windows.txt", classLoader)
        assertEquals("BIG5", big5Encoding)
    }

    /**
     * Tests detecting the encoding of a UTF-8 file with BOM (Mac).
     */
    @Test
    fun testDetectFileEncodingUtf8MacBom() {
        val utf8MacBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf8_mac_bom.txt", classLoader)
        assertEquals("UTF-8", utf8MacBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-8 file without BOM (Mac).
     */
    @Test
    fun testDetectFileEncodingUtf8MacNoBom() {
        val utf8MacNoBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf8_mac_no_bom.txt", classLoader)
        assertEquals("UTF-8", utf8MacNoBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-8 file with BOM (Windows).
     */
    @Test
    fun testDetectFileEncodingUtf8WindowsBom() {
        val utf8WindowsBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf8_windows_bom.txt", classLoader)
        assertEquals("UTF-8", utf8WindowsBomEncoding)
    }

    /**
     * Tests detecting the encoding of a UTF-8 file without BOM (Windows).
     */
    @Test
    fun testDetectFileEncodingUtf8WindowsNoBom() {
        val utf8WindowsNoBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf8_windows_no_bom.txt", classLoader)
        assertEquals("UTF-8", utf8WindowsNoBomEncoding)
    }

    @Test
    fun testDetectFileEncodingWindows1252Explicitly() {
        // Überspringe die automatische Erkennung und erlaube nur explizite Deklaration
        val expectedEncoding = "windows-1252"

        // Lies die Datei mit der explizit festgelegten Codierung
        val content = FileUtils.readEntireTextFile("src/test/resources/testfile_windows-1252.txt", Charset.forName(expectedEncoding), classLoader)

        // Stelle sicher, dass der Text korrekt gelesen wurde
        assertTrue(content.contains("Höheres Leben"))

        // Debug-Ausgabe zur Bestätigung
        println("Windows-1252 file content: $content")
    }

    @Test
    fun testDetectFileEncodingWithFilePath() {
        // Test UTF-8 with BOM (Unix) by file path
        val utf8UnixBomEncoding = FileUtils.detectFileEncoding("src/test/resources/testfile_utf8_unix_bom.txt", classLoader)
        assertEquals("UTF-8", utf8UnixBomEncoding)
    }
}