package cc.duduhuo.git.conflict.tool;

import cc.duduhuo.git.conflict.Constants;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import org.jetbrains.annotations.NotNull;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/28 20:27
 * Description:
 * Remarks:
 * =======================================================
 */
public final class NotificationTools {
    public static void showNotification(@NotNull String title, @NotNull String content, @NotNull NotificationType type) {
        Notification notification = new Notification(BundleTools.getValue(Constants.BundleKey.GROUP_DISPLAY_ID), title,
            content, type);
        Notifications.Bus.notify(notification);
    }
}
