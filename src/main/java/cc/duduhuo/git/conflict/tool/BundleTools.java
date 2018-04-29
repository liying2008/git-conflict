package cc.duduhuo.git.conflict.tool;

import com.intellij.CommonBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:57
 * Description:
 * Remarks:
 * =======================================================
 */
public final class BundleTools {
    private static final String BUNDLE_FILE = "GitConflictBundle";
    private static final ResourceBundle sBundle;

    static {
        sBundle = ResourceBundle.getBundle(BUNDLE_FILE);
    }

    public static String getValue(@NotNull String key) {
        return CommonBundle.message(sBundle, key, "%s");
    }
}
