package com.vitorblog.antiop.language

import com.vitorblog.antiop.model.Language

class `PT-BR` : Language {

    override
    var updateMessage = "Uma atualização disponivel v%s! Download: %s"

    override
    var selectedLanguageMessage = "O plugin esta usando a linguagem PT-BR"

    override
    var scanDisabledMessage = "O scan esta desativado, desativando o plugin..."

    override
    var loadedJarsMessage = "%d jars carrecadas! Verificando..."

    override
    var loadedCodeMessage = "%d classes de %s carregadas"

    override
    var forceopMessage = "Um ForceOp foi encontrado em %s %s:%d"

    override
    var deleteFileMessage = "O arquivo %s foi deletado"

    override
    var extractFileMessage = "O arquivo %s foi extraído para %s"

    override
    var finishMessage = "O scan foi terminado, encontramos %d arquivos com código malicioso! Por favor desligue o scan na config e reinicie o servidor"

    override
    var donateMessage = "[BlogAntiForceOp] §bDoe para me manter atualizado"

    override
    var donateMessage2 = "[BlogAntiForceOp] §bEu te ajudei? Se sim por favor doe para me manter atualizado"

    override
    var donateLinks = "[BlogAntiForceOp] §bLinks de doação: §f§nhttps://vitorblog.com/donate/§b (MercadoPago) or §f§nhttp://bit.ly/2PWXjiQ§b (PayPal)"

}