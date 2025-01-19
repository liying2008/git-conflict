package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.PersistentState
import cc.duduhuo.git.conflict.setting.SettingsService
import com.intellij.openapi.application.ApplicationManager


/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 21:02
 * Description:
 * Remarks:
 * =======================================================
 */
object GlobalSettings {

    fun getPersistentState(): PersistentState {
        // println(">>>> getPersistentState <<<<")
        val service = ApplicationManager.getApplication().getService(SettingsService::class.java)
        return service.state
    }
}
