package com.vitorblog.antiop

import com.vitorblog.antiop.manager.JarManager
import com.vitorblog.antiop.manager.LogManager
import com.vitorblog.antiop.model.Config
import com.vitorblog.antiop.model.Language
import com.vitorblog.antiop.process.ConfigProcess
import com.vitorblog.antiop.process.JarProcess
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.URL

class Main : JavaPlugin() {

    companion object {
        lateinit var instance: Main
    }

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

        Language.SELECTED_LANGUAGE.log()
        Language.FIRST_DONATE_MESSAGE.log()
        Language.DONATE_LINK.log()

        if (!(Config.SCAN.obj as Boolean)) {
            checkUpdate()
            Language.SCAN_DISABLED.log()
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
                Language.UPDATE_MESSAGE.log(
                    latestVersion,
                    "https://github.com/VitorBlog/BlogAntiForceOp/releases/download/$latestVersion/BlogAntiForceOp.jar"
                )
            }

        } catch (e: Exception) {

            logger.info("Could not check for updates.")

        }

    }

}
