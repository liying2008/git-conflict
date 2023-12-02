package cc.duduhuo.git.conflict.setting

import cc.duduhuo.git.conflict.model.PersistentState
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 18:12
 * Description: Settings service implementation
 * Remarks:
 * =======================================================
 */
@State(
    name = "GitConflict",
    storages = [Storage(value = "cc.duduhuo.git.conflict.2.applicationConfigurable.xml")]
)
class SettingsService : PersistentStateComponent<PersistentState?> {
    var stateValue: PersistentState = PersistentState()

    override fun getState(): PersistentState {
        return stateValue
    }

    override fun loadState(state: PersistentState) {
        XmlSerializerUtil.copyBean(state, stateValue)
    }

    override fun noStateLoaded() {
        val state = PersistentState()
        loadState(state)
    }
}
