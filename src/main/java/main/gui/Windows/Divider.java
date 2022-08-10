package main.gui.Windows;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.app.Color;

public class Divider extends GuiWindow {

    public Divider() {
        super("Divider");
    }

    @Override
    public void Render() {
        ImDrawList list = ImGui.getWindowDrawList();
        list.addLine(ImGui.getCursorScreenPosX(), ImGui.getCursorScreenPosY(), ImGui.getCursorScreenPosX() + ImGui.getWindowWidth(), ImGui.getCursorScreenPosY(), ImColor.floatToColor(1, 1, 1, 0.5f), 2);
    }

}
