package com.vitorblog.antiop.language

import com.vitorblog.antiop.model.Language

class `EN-US` : Language {

    override
    var updateMessage = "An update available v%s! Download: %s"

    override
    var selectedLanguageMessage = "The plugin is using de EN-US language"

    override
    var scanDisabledMessage = "The scan is disabled in the config, disabling the plugin..."

    override
    var loadedJarsMessage = "Loaded %d jars! Scanning..."

    override
    var loadedCodeMessage = "Loaded %d classes from %s"

    override
    var forceopMessage = "A ForceOp was found at %s in %s:%d"

    override
    var deleteFileMessage = "The file %s was deleted"

    override
    var extractFileMessage = "The file %s was extracted to %s"

    override
    var finishMessage = "The scan is over, we found %d files with malicious code! Please disable the scan in the config and restart the server"

    override
    var donateMessage = "[BlogAntiForceOp] §bDonate to keep me being updated"

    override
    var donateMessage2 = "[BlogAntiForceOp] §bI helped you? If yes please donate to keep me being updated"

    override
    var donateLinks = "[BlogAntiForceOp] §bDonate links: §f§nhttps://vitorblog.com/donate/§b (MercadoPago) or §f§nhttp://bit.ly/2PWXjiQ§b (PayPal)"

}