package tutorials.files

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.io.File

/**
 * Test class for FileUtils.
 * This test suite verifies the correct functioning of reading, encoding detection, encoding conversion,
 * and handling of both text and binary files using various encodings and file formats.
 */
class FileUtilsTest {

    private val classLoader = Thread.currentThread().contextClassLoader

    /**
     * Tests reading an entire text file with different encodings and BOM/no-BOM variations.
     */
    @Test
    fun testReadEntireTextFile() {
        // UTF-8 (Unix, without BOM)
        val utf8UnixNoBomContent = FileUtils.readEntireTextFile("testfile_utf8_unix_no_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8UnixNoBomContent.contains("Höheres Leben"))

        // UTF-8 (Unix, with BOM)
        val utf8UnixBomContent = FileUtils.readEntireTextFile("testfile_utf8_unix_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8UnixBomContent.contains("Höheres Leben"))

        // UTF-8 (Windows, without BOM)
        val utf8WindowsNoBomContent = FileUtils.readEntireTextFile("testfile_utf8_windows_no_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8WindowsNoBomContent.contains("Höheres Leben"))

        // UTF-8 (Windows, with BOM)
        val utf8WindowsBomContent = FileUtils.readEntireTextFile("testfile_utf8_windows_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8WindowsBomContent.contains("Höheres Leben"))

        // UTF-8 (Mac, without BOM)
        val utf8MacNoBomContent = FileUtils.readEntireTextFile("testfile_utf8_mac_no_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8MacNoBomContent.contains("Höheres Leben"))

        // UTF-8 (Mac, with BOM)
        val utf8MacBomContent = FileUtils.readEntireTextFile("testfile_utf8_mac_bom.txt", Charsets.UTF_8, classLoader)
        assertTrue(utf8MacBomContent.contains("Höheres Leben"))

        // UTF-16 BE (with BOM)
        val utf16BeBomContent = FileUtils.readEntireTextFile("testfile_utf16_be_bom.txt", Charsets.UTF_16BE, classLoader)
        assertTrue(utf16BeBomContent.contains("Höheres Leben"))

        // UTF-16 BE (without BOM)
        val utf16BeNoBomContent = FileUtils.readEntireTextFile("testfile_utf16_be_no_bom.txt", Charsets.UTF_16BE, classLoader)
        assertTrue(utf16BeNoBomContent.contains("Höheres Leben"))

        // UTF-16 LE (with BOM)
        val utf16LeBomContent = FileUtils.readEntireTextFile("testfile_utf16_le_bom.txt", Charsets.UTF_16LE, classLoader)
        assertTrue(utf16LeBomContent.contains("Höheres Leben"))

        // UTF-16 LE (without BOM)
        val utf16LeNoBomContent = FileUtils.readEntireTextFile("testfile_utf16_le_no_bom.txt", Charsets.UTF_16LE, classLoader)
        assertTrue(utf16LeNoBomContent.contains("Höheres Leben"))

        // Windows-1252
        val windows1252Content = FileUtils.readEntireTextFile("testfile_windows-1252.txt", Charset.forName("windows-1252"), classLoader)
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Big5 (Traditional Chinese for Windows)
        val big5Content = FileUtils.readEntireTextFile("testfile_big5_traditionell_windows.txt", Charset.forName("Big5"), classLoader)
        assertTrue(big5Content.contains("更高的生活"))
    }

    /**
     * Tests detecting the encoding of various files using FileUtils.detectFileEncoding.
     * This function detects the encoding of the files and verifies the correct encoding.
     */
    @Test
    fun testDetectFileEncoding() {
        // Detect encoding of UTF-8 (Unix, without BOM)
        val utf8UnixNoBomEncoding = FileUtils.detectFileEncoding("testfile_utf8_unix_no_bom.txt", classLoader)
        assertEquals("UTF-8", utf8UnixNoBomEncoding)

        // Detect encoding of UTF-8 (Unix, with BOM)
        val utf8UnixBomEncoding = FileUtils.detectFileEncoding("testfile_utf8_unix_bom.txt", classLoader)
        assertEquals("UTF-8", utf8UnixBomEncoding)

        // Detect encoding of UTF-16 BE (with BOM)
        val utf16BeBomEncoding = FileUtils.detectFileEncoding("testfile_utf16_be_bom.txt", classLoader)
        assertEquals("UTF-16BE", utf16BeBomEncoding)

        // Test falls back to UTF-8 or windows-1252 for non-BOM files
        val utf16BeNoBomEncoding = FileUtils.detectFileEncoding("testfile_utf16_be_no_bom.txt", classLoader)
        if (utf16BeNoBomEncoding != "UTF-16BE") {
            println("Note: Detected encoding for UTF-16 without BOM was: $utf16BeNoBomEncoding")
        }
        assertTrue(utf16BeNoBomEncoding == "UTF-16BE" || utf16BeNoBomEncoding == "windows-1252")
    }

    /**
     * Tests reading a binary file (image) using FileUtils.readBinaryFile.
     * This ensures that binary data is read correctly.
     */
    @Test
    fun testReadBinaryFile() {
        val binaryData = FileUtils.readBinaryFile("kotlin.png", classLoader)
        assertTrue(binaryData.isNotEmpty())
        assertTrue(binaryData.size > 1000)  // Ensure the file size is reasonable for an image
    }

    /**
     * Tests converting a file's encoding from UTF-8 to Windows-1252 and back to UTF-8 using FileUtils.convertFileEncoding.
     * This test ensures that the conversion process maintains the file content correctly.
     */
    @Test
    fun testConvertFileEncoding() {
        val tempOutputPath = "src/test/resources/temp_output.txt"

        // Erstelle die Datei für den Test (falls sie nicht existiert)
        val outputFile = File(tempOutputPath)
        outputFile.createNewFile()

        // Convert from UTF-8 (Unix) to Windows-1252
        FileUtils.convertFileEncoding(
            "testfile_utf8_unix_no_bom.txt",
            tempOutputPath,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252"),
            classLoader = classLoader
        )

        val windows1252Content = FileUtils.readEntireTextFile(tempOutputPath, Charset.forName("windows-1252"))
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Convert back from Windows-1252 to UTF-8
        FileUtils.convertFileEncoding(
            tempOutputPath,
            tempOutputPath,
            fromEncoding = Charset.forName("windows-1252"),
            toEncoding = Charsets.UTF_8,
            classLoader = classLoader
        )

        val utf8Content = FileUtils.readEntireTextFile(tempOutputPath, Charsets.UTF_8)
        assertTrue(utf8Content.contains("Höheres Leben"))

        // Clean up temporary output file
        outputFile.delete()
    }

    /**
     * Tests converting a file's encoding using buffered streams.
     * This ensures that large files can be processed without loading the entire content into memory.
     */
    @Test
    fun testConvertFileEncodingWithBufferedStreams() {
        val tempOutputPath = "src/test/resources/temp_output_streams.txt"

        // Erstelle die Datei für den Test (falls sie nicht existiert)
        val outputFile = File(tempOutputPath)
        outputFile.createNewFile()

        // Convert from UTF-8 to Windows-1252 using buffered streams
        FileUtils.convertFileEncodingWithBufferedStreams(
            "testfile_utf8_unix_no_bom.txt",
            tempOutputPath,
            fromEncoding = Charsets.UTF_8,
            toEncoding = Charset.forName("windows-1252"),
            classLoader = classLoader
        )

        val windows1252Content = FileUtils.readEntireTextFile(tempOutputPath, Charset.forName("windows-1252"))
        assertTrue(windows1252Content.contains("Höheres Leben"))

        // Clean up temporary output file
        outputFile.delete()
    }
}