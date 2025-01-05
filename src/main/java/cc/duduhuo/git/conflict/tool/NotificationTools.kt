package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Constants
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/28 20:27
 * Description:
 * Remarks:
 * =======================================================
 */
object NotificationTools {
    fun showNotification(
        title: String, content: String, type: NotificationType,
        project: Project? = null,
        actions: Collection<NotificationAction>? = null
    ) {
        val notification = Notification(
            Constants.Resource.NOTIFICATION_GROUP_DISPLAY_ID, title, content, type
        )
        if (actions != null) {
            notification.addActions(actions)
        }
        Notifications.Bus.notify(notification, project)
    }
}
