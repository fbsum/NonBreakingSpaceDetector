package com.fbsum.plugin.nonbreakingspacedetector

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.xml.XmlDocument

object Utils {

    private val log = Logger.getInstance(Utils::class.java)

    fun apply(xmlDocument: XmlDocument?) {
        if (xmlDocument == null || xmlDocument.rootTag == null) {
            return
        }
        val rootTag = xmlDocument.rootTag
        val subTags = rootTag!!.subTags
        subTags.iterator().forEach {
            it.value.setEscapedText(fix(it.value.trimmedText))
            log.info(it.value.text)
            log.info(it.value.trimmedText)
        }
    }

    private fun fix(text: String): String {
        return text.replace('\u00A0', ' ')
    }
}