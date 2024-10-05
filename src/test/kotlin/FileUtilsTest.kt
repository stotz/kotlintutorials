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
}