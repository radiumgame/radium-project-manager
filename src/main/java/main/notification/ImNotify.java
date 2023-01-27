package main.notification;

import imgui.*;
import imgui.app.Window;
import imgui.flag.ImGuiWindowFlags;

import java.util.ArrayList;
import java.util.List;

public class ImNotify {

    private static final List<ImNotification> notificationList = new ArrayList<>();

    private static ImFont bigFont;

    public static void initialize(ImFont largeFont) {
        bigFont = largeFont;
    }

    public static void notify(String title, String content) {
        notificationList.add(new ImNotification(title, content, 4.0f, ImNotification.NotificationType.Info));
    }

    public static void notify(ImNotification notification) {
        notificationList.add(notification);
    }

    public static void deleteNotification(ImNotification notification) {
        notificationList.remove(notification);
    }

    private static final int flags = ImGuiWindowFlags.NoBackground |
            ImGuiWindowFlags.NoTitleBar |
            ImGuiWindowFlags.NoMove |
            ImGuiWindowFlags.NoResize |
            ImGuiWindowFlags.NoInputs |
            ImGuiWindowFlags.NoMouseInputs |
            ImGuiWindowFlags.NoScrollbar |
            ImGuiWindowFlags.NoNav;

    public static void renderNotifications() {
        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(Window.Width, Window.Height);
        ImGui.begin("##NOTIFICATION_WINDOW", flags);

        ImDrawList list = ImGui.getWindowDrawList();
        for (int i = 0; i < notificationList.size(); i++) {
            float firstOpacity = notificationList.get(0).opacity;
            float beforePosition = Window.Height - 100;
            if (i != 0) {
                beforePosition = Window.Height - 100 - ((i - 1) * 100);
            }

            ImNotification notification = notificationList.get(i);
            notification.update();

            ImVec2 textSize = new ImVec2();
            ImGui.calcTextSize(textSize, notification.content);
            float Width = textSize.x + 50;
            Width = Math.max(Width, 450);
            float yPos = lerp(beforePosition, Window.Height - 100 - (i * 100), firstOpacity);

            list.addRectFilled(Window.Width - Width - 50, yPos, Window.Width - 10, yPos + 90, ImColor.floatToColor(notification.backgroundColor[0], notification.backgroundColor[1], notification.backgroundColor[2], notification.opacity), 30);
            list.addImage(notification.icon, Window.Width - Width - 40, yPos + 20, Window.Width - Width + 10, yPos + 70);

            ImGui.pushFont(bigFont);
            list.addText(Window.Width - Width + 15, yPos + 5, ImColor.floatToColor(notification.textColor[0], notification.textColor[1], notification.textColor[2], notification.opacity), notification.title);
            ImGui.popFont();

            list.addText(Window.Width - Width + 20, yPos + 35, ImColor.floatToColor(notification.textColor[0], notification.textColor[1], notification.textColor[2], notification.opacity), notification.content);
        }

        ImGui.end();
    }

    private static float lerp(float one, float two, float t) {
        return one + (two - one) * t;
    }

}