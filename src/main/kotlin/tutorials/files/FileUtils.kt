package tutorials.files

import java.io.File
import java.io.FileNotFoundException
import java.nio.charset.Charset

object FileUtils {

    /**
     * Reads an entire text file from the classpath or file system.
     * If the file is found in the resources, it reads from there; otherwise, it attempts to read from the file system.
     */
    fun readEntireTextFile(filePath: String, charset: Charset = Charsets.UTF_8, classLoader: ClassLoader? = Thread.currentThread().contextClassLoader): String {
        val file = resolveFilePath(filePath, classLoader)
        return file.readText(charset)
    }

    /**
     * Resolves the file path, either from the classpath or directly from the file system.
     */
    fun resolveFilePath(filePath: String, classLoader: ClassLoader?): File {
        val resource = classLoader?.getResource(filePath)
        return if (resource != null) {
            File(resource.toURI())
        } else {
            val file = File(filePath)
            if (!file.exists()) {
                throw FileNotFoundException("File not found: $filePath")
            }
            file
        }
    }

    /**
     * Converts a file's encoding and writes the result to a new file.
     */
    fun convertFileEncoding(
        inputFilePath: String,
        outputFilePath: String,
        fromEncoding: Charset = Charsets.UTF_8,
        toEncoding: Charset = Charsets.UTF_8,
        classLoader: ClassLoader? = Thread.currentThread().contextClassLoader
    ) {
        val inputFile = File(inputFilePath)
        val outputFile = File(outputFilePath)

        if (!inputFile.exists()) {
            throw FileNotFoundException("File not found: $inputFilePath")
        }

        inputFile.reader(fromEncoding).use { reader ->
            outputFile.writer(toEncoding).use { writer ->
                reader.copyTo(writer)
            }
        }
    }

    /**
     * Converts a file's encoding using buffered streams.
     */
    fun convertFileEncodingWithBufferedStreams(
        inputFilePath: String,
        outputFilePath: String,
        fromEncoding: Charset = Charsets.UTF_8,
        toEncoding: Charset = Charsets.UTF_8,
        classLoader: ClassLoader? = Thread.currentThread().contextClassLoader
    ) {
        val inputFile = File(inputFilePath)
        val outputFile = File(outputFilePath)

        if (!inputFile.exists()) {
            throw FileNotFoundException("File not found: $inputFilePath")
        }

        inputFile.inputStream().buffered().reader(fromEncoding).use { reader ->
            outputFile.outputStream().buffered().writer(toEncoding).use { writer ->
                reader.copyTo(writer)
            }
        }
    }

    /**
     * Reads a binary file from the file system.
     */
    fun readBinaryFile(filePath: String, classLoader: ClassLoader? = Thread.currentThread().contextClassLoader): ByteArray {
        val file = File(filePath)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $filePath")
        }
        return file.readBytes()
    }

    /**
     * Detects the file encoding using juniversalchardet.
     */
    fun detectFileEncoding(filePath: String, classLoader: ClassLoader?): String {
        // This is a placeholder for the actual encoding detection logic using juniversalchardet
        val file = resolveFilePath(filePath, classLoader)
        return "UTF-8"  // Default to UTF-8 in this example
    }
}
