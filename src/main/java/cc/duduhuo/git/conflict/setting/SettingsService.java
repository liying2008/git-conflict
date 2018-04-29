package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.model.PersistentState;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 17:47
 * Description: Settings service interface
 * Remarks:
 * =======================================================
 */
public interface SettingsService {
    PersistentState state = new PersistentState();
}
