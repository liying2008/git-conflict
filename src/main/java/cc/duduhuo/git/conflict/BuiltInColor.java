package cc.duduhuo.git.conflict;

import cc.duduhuo.git.conflict.model.MarkColor;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/25 21:28
 * Description:
 * Remarks:
 * =======================================================
 */
public interface BuiltInColor {
    String AUTO_SCHEME_NAME = "Auto";
    String INTELLIJ_SCHEME_NAME = "IntelliJ";
    String DARCULA_SCHEME_NAME = "Darcula";

    String DEFAULT_SCHEME = AUTO_SCHEME_NAME;

    MarkColor AUTO = new MarkColor(AUTO_SCHEME_NAME, true, 0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff,
        0x2f7366, 0x25403b, 0x2f628f, 0x25394b);
    MarkColor INTELLIJ = new MarkColor(INTELLIJ_SCHEME_NAME, true, 0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff);
    MarkColor DARCULA = new MarkColor(DARCULA_SCHEME_NAME, true, 0x2f7366, 0x25403b, 0x2f628f, 0x25394b);
}
