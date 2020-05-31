package cc.duduhuo.git.conflict.action.operation

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2020/5/30 16:38
 * Description:
 * Remarks:
 * =======================================================
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class IntDef(
    /** Defines the allowed constants for this element  */
    val value: IntArray
)
