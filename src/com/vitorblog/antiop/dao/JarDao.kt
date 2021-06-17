package com.vitorblog.antiop.dao

import com.vitorblog.antiop.model.Jar
import java.io.File

object JarDao {

    val JARS = hashMapOf<File, Jar>()

    fun add(jar: Jar) = JARS.put(jar.file, jar)

    fun get(file: File) = JARS[file]

}