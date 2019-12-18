package com.vitorblog.antiop.process

import com.vitorblog.antiop.Main
import com.vitorblog.antiop.dao.JarDao
import com.vitorblog.antiop.manager.JarManager
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Jar
import java.io.File

class JarProcess {

    fun load(){
        val whitelist = Config.WHITELIST.obj as List<String>
        for (file in File(".").listFiles().filter { it.extension.equals("jar") && !whitelist.contains(it.name) }){
            JarDao.add(Jar(file, hashMapOf()))
        }

        for (file in File("plugins/").listFiles().filter { it.extension.equals("jar") && !whitelist.contains(it.name) }){
            JarDao.add(Jar(file, hashMapOf()))
        }

        Main.instance.logger.info(Main.instance.language.loadedJarsMessage.format(JarDao.JARS.size))

        Main.instance.jarManager.decompileJars()
    }

}