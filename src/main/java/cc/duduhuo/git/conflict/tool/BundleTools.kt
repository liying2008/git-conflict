package cc.duduhuo.git.conflict.tool

import com.intellij.CommonBundle
import java.util.*

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:57
 * Description:
 * Remarks:
 * =======================================================
 */
object BundleTools {
    private const val BUNDLE_FILE = "GitConflictBundle"
    private var sBundle: ResourceBundle = ResourceBundle.getBundle(BUNDLE_FILE)

    fun getValue(key: String): String {
        return CommonBundle.message(sBundle, key, "%s")
    }
}