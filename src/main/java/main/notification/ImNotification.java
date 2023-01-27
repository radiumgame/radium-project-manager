package main.notification;

import imgui.ImGui;
import main.Texture;

public class ImNotification {

    public String title;
    public String content;

    public int icon;
    public float life;
    public float opacity = 0.0f;

    public float[] backgroundColor = new float[] { 0.3f, 0.3f, 0.3f };
    public float[] textColor = new float[] { 1.0f, 1.0f, 1.0f };

    public ImNotification(String title, String content, float lifetime, NotificationType notificationType) {
        this.title = title;
        this.content = content;
        this.life = lifetime;

        String iconPath = "";
        switch (notificationType) {
            case Info:
                iconPath = "assets/notification/log.png";
            case Warning:
                iconPath = "assets/notification/warning.png";
            case Error:
                iconPath = "assets/notification/error.png";
        }
        icon = Texture.LoadTexture(iconPath);
    }

    private boolean fading = false;
    public void update() {
        float deltaTime = ImGui.getIO().getDeltaTime();

        life -= deltaTime;
        if (!fading) opacity += deltaTime * 4.0f;
        if (opacity >= 1) opacity = 1.0f;

        if (life <= 0.5f) {
            fading = true;
            opacity -= deltaTime;
        }
        if (opacity <= 0) opacity = 0.0f;

        if (life <= 0) {
            ImNotify.deleteNotification(this);
        }
    }

    public static enum NotificationType {

        Info,
        Warning,
        Error

    }

}