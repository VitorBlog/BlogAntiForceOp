package com.vitorblog.antiop.manager

import java.io.File

class LogManager {
    val file = File("plugins/BlogAntiForceOp/logs.txt")

    fun createFile(){
        if (!file.exists()){
            file.createNewFile()
        }
    }

    fun write(s:String){
        file.writeText("${file.readText()}$s\n")
    }

}