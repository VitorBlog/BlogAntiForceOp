package com.vitorblog.antiop.process

import com.vitorblog.antiop.Main
import com.vitorblog.antiop.dao.JarDao
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Jar
import com.vitorblog.antiop.model.Language
import java.io.File

class JarProcess {

    fun load() {

        val whitelist = Config.WHITELIST.obj as List<*>

        for (file in File(".").listFiles().filter { it.extension == "jar" && !whitelist.contains(it.name) }) {
            JarDao.add(Jar(file, hashMapOf()))
        }

        Language.LOADED_JARS.log(JarDao.JARS.size)

        Main.instance.jarManager.decompileJars()
    }

}