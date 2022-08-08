package main.gui.Windows;

import imgui.ImGui;
import imgui.app.Application;
import imgui.flag.ImGuiWindowFlags;

public abstract class GuiWindow {

    public String title;

    private static final int DevWindowFlags = ImGuiWindowFlags.None;
    private static final int WindowFlags = ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoBackground | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoDocking;

    public GuiWindow(String title) {
        this.title = title;
    }

    public void Update() {
        ImGui.begin(title, Application.DevMode ? DevWindowFlags : WindowFlags);
        Render();
        ImGui.end();
    }

    public abstract void Render();

}
