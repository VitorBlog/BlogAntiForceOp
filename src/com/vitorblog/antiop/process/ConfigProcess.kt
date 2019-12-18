package com.vitorblog.antiop.process

import com.vitorblog.antiop.model.Config
import org.bukkit.configuration.file.FileConfiguration

class ConfigProcess {

    fun load(config:FileConfiguration){
        Config.LANGUAGE.obj = config.getString("Language")
        Config.SCAN.obj = config.getBoolean("Scan")
        Config.WHITELIST.obj = config.getStringList("Whitelist")
        Config.DELETE.obj = config.getBoolean("DeleteMaliciousJars")
        Config.EXTRACT.obj = config.getBoolean("ExtractMaliciousClasses")
    }

}