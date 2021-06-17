package com.vitorblog.antiop.manager

import com.vitorblog.antiop.Main
import com.vitorblog.antiop.dao.JarDao
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Jar
import com.vitorblog.antiop.sink.CFRSink
import org.benf.cfr.reader.api.CfrDriver
import org.bukkit.Bukkit
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class JarManager {

    private val maliciousLines: AtomicInteger = AtomicInteger(0);

    fun decompileJars(){
        for (jar in JarDao.JARS.values){
            val driver = CfrDriver.Builder().withOutputSink(CFRSink(jar)).build()
            driver.analyse(listOf("${jar.file.absoluteFile}"))

            JarDao.add(jar)
            Main.instance.logger.info(Main.instance.language.loadedCodeMessage.format(jar.files.size, jar.file.name))
        }

        searchForMaliciousJars()
    }

    fun searchForMaliciousJars(){
        for (jar in JarDao.JARS.values){
            for ((file, code) in jar.files){

                val lines = code.split("\n")

                for ((i, line) in lines.withIndex()){

                    if (line.maliciousLine()){

                        // Count one malicious line.
                        maliciousLines.getAndIncrement()

                        val msg = Main.instance.language.forceopMessage.format(jar.file.name, file, i-2)
                        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

                        Main.instance.logManager.write("[${simpleDateFormat.format(Date())}] $msg")
                        Main.instance.logger.info(msg)

                        if (Config.DELETE.obj as Boolean){
                            jar.file.delete()
                            Main.instance.logger.info(Main.instance.language.deleteFileMessage.format(jar.file.name))
                        }

                        if (Config.EXTRACT.obj as Boolean){
                            extractFile(jar, file, code)
                            Main.instance.logger.info(Main.instance.language.extractFileMessage.format(jar.file.name, "plugins/BlogAntiForceOp/${jar.file.nameWithoutExtension}/$file"))
                        }
                    }

                }

            }
        }

        finish()
    }

    fun extractFile(jar:Jar, file:String, code:String){
        val folder = File("plugins/BlogAntiForceOp/${jar.file.nameWithoutExtension}")
        folder.mkdir()

        val codeFile = File("${folder.path}/$file.java")
        codeFile.createNewFile()
        codeFile.writeText(code)
    }

    fun String.maliciousLine():Boolean {
        return this.contains("setOp") || this.contains("/op") || this.contains("/deop")
    }

    fun finish(){
        Main.instance.logger.info(Main.instance.language.finishMessage.format(maliciousLines.get()))
        Bukkit.getConsoleSender().sendMessage(Main.instance.language.donateMessage2)
        Bukkit.getConsoleSender().sendMessage(Main.instance.language.donateLinks)
    }
}
