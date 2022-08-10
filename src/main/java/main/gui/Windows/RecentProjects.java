package main.gui.Windows;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Color;
import java.io.File;

import main.Main;
import main.Settings;
import main.gui.Popup.GuiPopup;

import java.util.List;

public class RecentProjects extends GuiWindow {

    public static String selectedProject;

    private static final Color selectedColor = new Color(11f / 255f, 90f / 255f, 113f / 255f, 1f);

    public RecentProjects() {
        super("Recent Projects");
    }

    @Override
    public void Render() {
        ImGui.setCursorScreenPos(ImGui.getWindowPosX(), ImGui.getCursorScreenPosY());

        List<String> projects = Settings.Instance.RecentProjects;
        for (int i = 0; i < projects.size(); i++) {
            RenderProject(projects.get(i));
        }
    }

    private void RenderProject(String project) {
        float ww = ImGui.getWindowWidth();
        float wh = ImGui.getWindowHeight();
        ImVec2 pos = ImGui.getCursorScreenPos();
        boolean selected = project.equals(selectedProject);
        boolean hovered = ImGui.isMouseHoveringRect(pos.x, pos.y, pos.x + ww, pos.y + 50) && !GuiPopup.IsPopupOpen();

        File projectFile = new File(project);

        int color = selected ? selectedColor.getImGuiCol() : 0;
        if (hovered && !selected) {
            color = ImColor.floatToColor(1, 1, 1, 0.15f);
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(pos.x, pos.y, pos.x + ww, pos.y + 50, color);
        ImGui.pushFont(Main.getFont("large bold"));
        drawList.addText(pos.x + 10, pos.y + 2.5f, Color.White.getImGuiCol(), projectFile.getName());
        ImGui.popFont();
        drawList.addText(pos.x + 10, pos.y + 27.5f, Color.White.getImGuiCol(), project);
        if (ImGui.isMouseClicked(0)) {
            if (hovered) {
                selectedProject = project;
            }
        }

        ImGui.setCursorScreenPos(pos.x + ww - 100, pos.y + 10);
        if (ImGui.button("Open##" + project.hashCode(), 60, 30)) {
            MainPanel.OpenProject(project);
        }

        ImGui.setCursorScreenPos(pos.x, pos.y + 50);
    }

}
