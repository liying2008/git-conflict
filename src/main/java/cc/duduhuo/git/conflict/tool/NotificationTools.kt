package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Constants
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/28 20:27
 * Description:
 * Remarks:
 * =======================================================
 */
object NotificationTools {
    fun showNotification(title: String, content: String, type: NotificationType) {
        val notification = Notification(
            Constants.Resource.NOTIFICATION_GROUP_DISPLAY_ID, title, content, type
        )
        Notifications.Bus.notify(notification)
    }
}