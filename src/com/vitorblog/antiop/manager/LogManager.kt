package com.vitorblog.antiop.manager

import java.io.File

class LogManager {

    private val file = File("plugins/BlogAntiForceOp/logs.txt")

    fun createFile() {

        if (!file.exists()) file.createNewFile()

    }

    fun write(string: String) {

        file.appendText("\n$string")

    }

}