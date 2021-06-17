package com.vitorblog.antiop.manager

import com.vitorblog.antiop.Main
import com.vitorblog.antiop.dao.JarDao
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Jar
import com.vitorblog.antiop.model.Language
import com.vitorblog.antiop.sink.CFRSink
import org.benf.cfr.reader.api.CfrDriver
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class JarManager {

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    private val maliciousLines: AtomicInteger = AtomicInteger(0)

    fun decompileJars() {

        for (jar in JarDao.JARS.values) {

            val driver = CfrDriver.Builder().withOutputSink(CFRSink(jar)).build()
            driver.analyse(listOf("${jar.file.absoluteFile}"))

            JarDao.add(jar)
            Language.LOADED_CODE.log(jar.files.size, jar.file.name)

        }

        searchForMaliciousJars()
    }

    private fun searchForMaliciousJars() {

        for (jar in JarDao.JARS.values) {
            for ((file, code) in jar.files) {

                for ((index, line) in code.split("\n").withIndex()) {

                    if (line.maliciousLine()) {

                        maliciousLines.getAndIncrement()

                        val message = Language.FORCE_OP_FOUND.format(jar.file.name, file, index - 2)

                        Main.instance.logManager.write("[${simpleDateFormat.format(Date())}] $message")
                        Main.instance.logger.info(message)

                        if (Config.DELETE.obj as Boolean) {

                            jar.file.delete()
                            Language.DELETE_FILE.log(jar.file.name)

                        }

                        if (Config.EXTRACT.obj as Boolean) {

                            extractFile(jar, file, code)
                            Language.EXTRACT_FILE.log(
                                jar.file.name,
                                "plugins/BlogAntiForceOp/${jar.file.nameWithoutExtension}/$file"
                            )

                        }

                    }

                }

            }
        }

        finish()

    }

    private fun extractFile(jar: Jar, file: String, code: String) {
        val folder = File("plugins/BlogAntiForceOp/${jar.file.nameWithoutExtension}")
        folder.mkdir()

        val codeFile = File("${folder.path}/$file.java")
        codeFile.createNewFile()
        codeFile.writeText(code)
    }

    private fun String.maliciousLine(): Boolean {

        return this.contains("setOp")
                || this.contains("/op")
                || this.contains("/deop")

    }

    private fun finish() {

        Language.FINISH_MESSAGE.log(maliciousLines.get())
        Language.SECOND_DONATE_MESSAGE.log()
        Language.DONATE_LINK.log()

    }
}
