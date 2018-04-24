package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.model.MarkColor;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 17:47
 * Description:
 * Remarks:
 * =======================================================
 */
public interface SettingsService {
    interface ColorSettings {
        MarkColor AUTO = new MarkColor(0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff,
            0x2f7366, 0x25403b, 0x2f628f, 0x25394b);
        MarkColor INTELLIJ = new MarkColor(0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff);
        MarkColor DARCULA = new MarkColor(0x2f7366, 0x25403b, 0x2f628f, 0x25394b);
    }

    MarkColor markColor = new MarkColor();
}
