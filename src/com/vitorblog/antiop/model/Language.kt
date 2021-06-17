package com.vitorblog.antiop.model

import com.vitorblog.antiop.Main

enum class Language(val english: String, val portuguese: String) {

    UPDATE_MESSAGE("An update available v%s! Download: %s", "Uma atualização disponivel v%s! Download: %s"),
    SELECTED_LANGUAGE("The plugin is using de EN-US language", "O plugin esta usando a linguagem PT-BR"),
    SCAN_DISABLED(
        "The scan is disabled in the config, disabling the plugin...",
        "O scan esta desativado, desligando o plugin..."
    ),
    LOADED_JARS("Loaded %d jars! Scanning...", "%d jars carregadas! Verificando..."),
    LOADED_CODE("Loaded %d classes from %s", "%d classes de %s carregadas"),
    FORCE_OP_FOUND("A ForceOp was found at %s in %s:%d", "Um ForceOp foi encontrado em %s %s:%d"),
    DELETE_FILE("The file %s was deleted", "O arquivo %s foi deletado"),
    EXTRACT_FILE("The file %s was extracted to %s", "O arquivo %s foi extraído para %s"),
    FINISH_MESSAGE(
        "The scan is over, we found %d files with malicious code! Please disable the scan in the config and restart the server",
        "O scan foi terminado, encontramos %d arquivos com código malicioso! Por favor desligue o scan na config e reinicie o servidor"
    ),
    FIRST_DONATE_MESSAGE("Donate to keep me being updated", "Doe para me manter atualizado"),
    SECOND_DONATE_MESSAGE(
        "I helped you? If yes please donate to keep me being updated",
        "Eu te ajudei? Se sim por favor doe para me manter atualizado"
    ),
    DONATE_LINK("Donate link: https://donate.vitorpaulo.dev", "Link de doação: https://donate.vitorpaulo.dev");

    fun log(vararg value: Any) {

        Main.instance.logger.info(format(value))

    }

    fun format(vararg value: Any) = (if (Config.LANGUAGE.obj == "PT-BR") portuguese else english).format(value)

}