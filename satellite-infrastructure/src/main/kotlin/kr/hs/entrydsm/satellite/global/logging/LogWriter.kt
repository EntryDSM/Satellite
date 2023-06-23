package kr.hs.entrydsm.satellite.global.logging

import javax.annotation.PreDestroy
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


@Component
class LogWriter(
    private val logProperties: LogProperties
) {
    private val file = File(logProperties.path, logProperties.name)
    private var writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file, true)))

    @Async
    @Throws(IOException::class)
    fun writeLog(content: String) {
        writer.write("${content}\n")
        writer.flush()
        if (isLogOverSize) {
            createZipFile()
        }
    }

    @PreDestroy
    @Throws(IOException::class)
    fun closeStream() {
        writer.close()
    }

    @get:Throws(IOException::class)
    private val isLogOverSize: Boolean
        get() {
            val size = BigDecimal.valueOf(Files.size(file.toPath()))
                .divide(BigDecimal.valueOf(1048576), 2, RoundingMode.HALF_UP)
                .toDouble()
            return logProperties.size < size
        }

    @Throws(IOException::class)
    private fun createZipFile() {
        val zipFile = File(logProperties.path, "${logProperties.name}.${now}.zip")
        ZipOutputStream(FileOutputStream(zipFile)).use {
            writeFileInputStream(it)
        }
        resetLogFile()
    }

    private fun writeFileInputStream(zipOutputStream: ZipOutputStream) {
        zipOutputStream.run {
            putNextEntry(ZipEntry(logProperties.name))
            FileInputStream(file).use { input ->
                write(input.readAllBytes())
            }
            closeEntry()
            finish()
        }
    }

    @Throws(IOException::class)
    private fun resetLogFile() {
        writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
    }

    private val now
        get() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
}