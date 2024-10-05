package tutorials.files

import java.io.*
import java.nio.charset.Charset
import org.mozilla.universalchardet.UniversalDetector

/**
 * Utility functions for file reading and encoding conversion.
 */
object FileUtils {

    /**
     * Resolves the file path. If the file path is absolute, it returns it directly.
     * If it's relative, it attempts to resolve the path using the provided ClassLoader.
     *
     * @param filePath The file path to resolve.
     * @param classLoader The ClassLoader to use for resolving relative paths (default is the current thread's context ClassLoader).
     * @return A File object representing the resolved path.
     * @throws FileNotFoundException If the file cannot be found.
     */
    private fun resolveFilePath(filePath: String, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): File {
        val file = File(filePath)

        // If the path is absolute, return the file directly
        return if (file.isAbsolute) {
            file
        } else {
            // Try to resolve the file as a resource from the classpath
            classLoader.getResource(filePath)?.let {
                File(it.toURI())
            } ?: throw FileNotFoundException("File not found: $filePath")
        }
    }

    /**
     * Reads the entire content of a text file using the specified encoding.
     *
     * @param filePath The path to the file to be read (absolute or relative).
     * @param encoding The character encoding to use (default is UTF-8).
     * @param classLoader The ClassLoader to use if the file path is relative.
     * @return The content of the file as a String.
     * @throws IOException If an error occurs while reading the file.
     */
    @Throws(IOException::class)
    fun readEntireTextFile(filePath: String, encoding: Charset = Charsets.UTF_8, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): String {
        val file = resolveFilePath(filePath, classLoader)
        if (!file.exists()) {
            throw IOException("File not found: $filePath")
        }
        return file.readText(encoding)
    }

    /**
     * Reads the entire content of a binary file.
     *
     * @param filePath The path to the binary file to be read (absolute or relative).
     * @param classLoader The ClassLoader to use if the file path is relative.
     * @return The content of the binary file as a ByteArray.
     * @throws IOException If an error occurs while reading the file.
     */
    @Throws(IOException::class)
    fun readBinaryFile(filePath: String, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): ByteArray {
        val file = resolveFilePath(filePath, classLoader)
        if (!file.exists()) {
            throw IOException("File not found: $filePath")
        }
        return file.readBytes()
    }

    /**
     * Converts a file from one encoding to another.
     * The default conversion is from UTF-8 to Windows-1252.
     *
     * @param inputFilePath The path to the input file (absolute or relative).
     * @param outputFilePath The path to the output file (absolute or relative).
     * @param fromEncoding The source encoding (default is UTF-8).
     * @param toEncoding The target encoding (default is Windows-1252).
     * @param classLoader The ClassLoader to use if the input/output file paths are relative.
     * @throws IOException If an error occurs while reading or writing the file.
     */
    @Throws(IOException::class)
    fun convertFileEncoding(
        inputFilePath: String,
        outputFilePath: String,
        fromEncoding: Charset = Charsets.UTF_8,
        toEncoding: Charset = Charset.forName("windows-1252"),
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ) {
        val inputFile = resolveFilePath(inputFilePath, classLoader)
        val outputFile = File(outputFilePath)

        if (!inputFile.exists()) {
            throw IOException("Input file not found: $inputFilePath")
        }

        val content = inputFile.readText(fromEncoding)
        outputFile.writeText(content, toEncoding)
    }

    /**
     * Converts a file's encoding using buffered streams.
     * This is useful for large files to avoid loading the entire content into memory.
     *
     * @param inputFilePath The path to the input file (absolute or relative).
     * @param outputFilePath The path to the output file (absolute or relative).
     * @param fromEncoding The source encoding (default is UTF-8).
     * @param toEncoding The target encoding (default is Windows-1252).
     * @param classLoader The ClassLoader to use if the input/output file paths are relative.
     * @throws IOException If an error occurs while reading or writing the file.
     */
    @Throws(IOException::class)
    fun convertFileEncodingWithBufferedStreams(
        inputFilePath: String,
        outputFilePath: String,
        fromEncoding: Charset = Charsets.UTF_8,
        toEncoding: Charset = Charset.forName("windows-1252"),
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ) {
        val inputFile = resolveFilePath(inputFilePath, classLoader)
        val outputFile = File(outputFilePath)

        if (!inputFile.exists()) {
            throw IOException("Input file not found: $inputFilePath")
        }

        BufferedReader(InputStreamReader(FileInputStream(inputFile), fromEncoding)).use { reader ->
            BufferedWriter(OutputStreamWriter(FileOutputStream(outputFile), toEncoding)).use { writer ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    writer.write(line)
                    writer.newLine()
                }
            }
        }
    }

    /**
     * Detects the encoding of a file using the juniversalchardet library.
     *
     * @param filePath The path to the file whose encoding needs to be detected (absolute or relative).
     * @param classLoader The ClassLoader to use if the file path is relative.
     * @return The detected encoding as a String, or null if encoding could not be detected.
     * @throws IOException If an error occurs while reading the file.
     */
    @Throws(IOException::class)
    fun detectFileEncoding(filePath: String, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): String? {
        val file = resolveFilePath(filePath, classLoader)
        if (!file.exists()) {
            throw IOException("File not found: $filePath")
        }

        val buffer = ByteArray(4096)
        val detector = UniversalDetector(null)

        FileInputStream(file).use { fis ->
            var bytesRead: Int
            while (fis.read(buffer).also { bytesRead = it } > 0 && !detector.isDone) {
                detector.handleData(buffer, 0, bytesRead)
            }
            detector.dataEnd()
        }

        return detector.detectedCharset
    }
}