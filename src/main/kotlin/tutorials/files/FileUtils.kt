package tutorials.files

import org.mozilla.universalchardet.UniversalDetector
import java.io.File
import java.io.FileNotFoundException
import java.nio.charset.Charset
import java.nio.file.Files

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
     *
     * @param filePath The file path to detect encoding.
     * @param classLoader The class loader to use to resolve file paths, if necessary.
     * @return The detected file encoding as a string, or "UTF-8" if the encoding couldn't be detected.
     */
    fun detectFileEncoding(filePath: String, classLoader: ClassLoader?): String {
        val file = resolveFilePath(filePath, classLoader)

        // Create a UniversalDetector instance
        val detector = UniversalDetector(null)

        // Read the file and feed the data to the detector
        Files.newInputStream(file.toPath()).use { inputStream ->
            val buffer = ByteArray(4096)
            var nread: Int
            while (inputStream.read(buffer).also { nread = it } > 0 && !detector.isDone) {
                detector.handleData(buffer, 0, nread)
            }
        }

        // Signal to the detector that we are done reading
        detector.dataEnd()

        // Get the detected encoding
        val encoding = detector.detectedCharset

        // Reset detector for potential reuse
        detector.reset()

        // Return the detected encoding, or default to UTF-8 if none was detected
        return encoding ?: "UTF-8"
    }
}
