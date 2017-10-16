package com.fbsum.plugin.nonbreakingspacedetector

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile

class FixNonBreakingSpaceAction : AnAction() {

    private val log = Logger.getInstance(FixNonBreakingSpaceAction::class.java)
    private val VALID_FILE_NAME = "strings.xml"

    override fun update(event: AnActionEvent) {
        super.update(event)
        val virtualFile = event.getData(LangDataKeys.VIRTUAL_FILE) ?: return
        event.presentation.isVisible = isValidFile(virtualFile)
    }

    /**
     * 判断当前显示的文件是否为 strings.xml
     */
    private fun isValidFile(file: VirtualFile): Boolean {
        return StringUtil.equals(file.name, VALID_FILE_NAME)
    }

    override fun actionPerformed(event: AnActionEvent) {
        val virtualFile = event.getData(LangDataKeys.VIRTUAL_FILE) ?: return
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val xmlFile: XmlFile = PsiManager.getInstance(project).findFile(virtualFile) as XmlFile
        object : WriteCommandAction.Simple<Any>(project) {
            @Throws(Throwable::class)
            override fun run() {
                Utils.apply(xmlFile.document)
            }
        }.execute()
    }
}
