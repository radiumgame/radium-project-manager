package main.gui.Popup;

import imgui.ImGui;
import imgui.app.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiPopup {

    private static List<GuiPopup> popups = new ArrayList<>();

    public String title;
    public PopupType popupType;

    protected int flags = 0;

    public GuiPopup(String title, PopupType popupType) {
        this.title = title;
        this.popupType = popupType;

        popups.add(this);
    }

    public void Update() {
        boolean isOpen = false;
        switch (popupType) {
            case Standard -> isOpen = ImGui.beginPopup(title, flags);
            case Modal -> isOpen = ImGui.beginPopupModal(title, flags);
        }

        if (isOpen) {
            Render();
            ImGui.endPopup();
        }
    }

    public void Open() {
        ImGui.openPopup(title);
        System.out.println("Open popup: " + title);
    }

    public void Center() {
        int ww = Window.Width;
        int wh = Window.Height;
        int pw = Math.round(ImGui.getWindowWidth());
        int ph = Math.round(ImGui.getWindowHeight());

        ImGui.setWindowPos(ww / 2 - pw / 2, wh / 2 - ph / 2);
    }

    public abstract void Render();

    public static void Open(String title) {
        popups.forEach(popup -> {
            if (popup.title.equals(title) && !ImGui.isPopupOpen(title)) {
                popup.Open();
            }
        });
    }

    public static void Update(String popup) {
        popups.forEach(popup1 -> {
            if (popup1.title.equals(popup)) {
                popup1.Update();
            }
        });
    }

    public static enum PopupType {

        Standard,
        Modal

    }

}
