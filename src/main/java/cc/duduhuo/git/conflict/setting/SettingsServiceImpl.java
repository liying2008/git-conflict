package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.model.PersistentState;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 18:12
 * Description:
 * Remarks:
 * =======================================================
 */
@State(name = "GitConflict",
    storages = {@Storage(value = "cc.duduhuo.git.conflict.1.applicationConfigurable.xml")})
public class SettingsServiceImpl implements SettingsService, PersistentStateComponent<PersistentState> {
    @Nullable
    @Override
    public PersistentState getState() {
        return XmlSerializerUtil.createCopy(state);
    }

    @Override
    public void loadState(@NotNull PersistentState state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    @Override
    public void noStateLoaded() {
        PersistentState state = new PersistentState();
        loadState(state);
    }
}
