package com.example;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;

public class MessageDialogAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        StringBuffer message = new StringBuffer(event.getPresentation().getText() + " Selected!");
        String description = event.getPresentation().getDescription();

        Navigatable nav = event.getData(CommonDataKeys.NAVIGATABLE);
        if (nav != null) {
            message.append(String.format("\nSelected Element: %s", nav));
        }
        Messages.showMessageDialog(project, message.toString(), description, Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent event) {
        Project project = event.getProject();
        event.getPresentation().setEnabledAndVisible(project != null);
    }
}
