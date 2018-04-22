package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.model.MarkColor;
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
    storages = {@Storage(value = "cc.duduhuo.git.conflict.applicationConfigurable.xml")})
public class SettingsServiceImpl implements SettingsService, PersistentStateComponent<MarkColor> {
    @Nullable
    @Override
    public MarkColor getState() {
        System.out.println("getState()");
        return XmlSerializerUtil.createCopy(markColor);
    }

    @Override
    public void loadState(@NotNull MarkColor markColor) {
        System.out.println("loadState()");
        XmlSerializerUtil.copyBean(markColor, this.markColor);
    }
}
