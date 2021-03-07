package com.example;

import org.jetbrains.annotations.NotNull;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class NotificationAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        NotificationGroupManager.getInstance()
                                .getNotificationGroup("Notification_Test")
                                .createNotification("Notification Demo", NotificationType.INFORMATION)
                                .notify(project);
    }
}
