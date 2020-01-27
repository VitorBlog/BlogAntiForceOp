package com.vitorblog.antiop

import com.vitorblog.antiop.language.`EN-US`
import com.vitorblog.antiop.language.`PT-BR`
import com.vitorblog.antiop.manager.JarManager
import com.vitorblog.antiop.manager.LogManager
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Language
import com.vitorblog.antiop.process.ConfigProcess
import com.vitorblog.antiop.process.JarProcess
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.util.stream.Collectors
import java.io.BufferedReader
import java.net.URL


class Main : JavaPlugin() {
    companion object { lateinit var instance:Main }
    lateinit var language:Language
    val jarManager = JarManager()
    val logManager = LogManager()

    override
    fun onEnable() {
        instance = this
        saveDefaultConfig()
        reloadConfig()

        checkUpdate()

        logManager.createFile()
        ConfigProcess().load(config)
        language = when (Config.LANGUAGE.obj.toString().toUpperCase()){
            "PT-BR" -> `PT-BR`()
            else -> `EN-US`()
        }
        logger.info(language.selectedLanguageMessage)
        Bukkit.getConsoleSender().sendMessage(Main.instance.language.donateMessage)
        Bukkit.getConsoleSender().sendMessage(Main.instance.language.donateLinks)

        if (!(Config.SCAN.obj as Boolean)){
            checkUpdate()
            logger.info(language.scanDisabledMessage)
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        JarProcess().load()
        checkUpdate()
    }

    // https://gist.github.com/VitorBlog/48ec363d9158a21b4ea603d370d89a5c
    fun checkUpdate() {
        val version = this.description.version

        try {
            val url = URL("https://api.github.com/repos/VitorBlog/BlogAntiForceOp/releases/latest")
            val connection = url.openConnection()

            val response = connection.getInputStream().reader().readText()

            val jsonObject = JSONParser().parse(response) as JSONObject
            val latestVersion = jsonObject["tag_name"] as String

            if (version != latestVersion) {
                logger.info(language.updateMessage.format(latestVersion, "https://github.com/VitorBlog/BlogAntiForceOp/releases/download/$latestVersion/BlogAntiForceOp.jar"))
            }

        } catch (e: Exception) {
                logger.info("Could not check for updates.")
        }

    }

}
