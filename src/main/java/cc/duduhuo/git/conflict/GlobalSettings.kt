package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.PersistentState
import cc.duduhuo.git.conflict.setting.SettingsService
import com.intellij.openapi.components.ServiceManager


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
        val service = ServiceManager.getService(SettingsService::class.java)
        return service.stateValue
    }
}
