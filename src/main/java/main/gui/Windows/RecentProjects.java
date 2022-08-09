package main.gui.Windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Color;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import main.Settings;

import java.util.List;

public class RecentProjects extends GuiWindow {

    public static String selectedProject;

    private static final Color selectedColor = new Color(11f / 255f, 90f / 255f, 113f / 255f, 1f);

    public RecentProjects() {
        super("Recent Projects");
    }

    @Override
    public void Render() {
        List<String> projects = Settings.Instance.RecentProjects;
        for (String project : projects) {
            RenderProject(project);
        }
    }

    private void RenderProject(String project) {
        float ww = ImGui.getWindowWidth();
        float wh = ImGui.getWindowHeight();
        ImVec2 pos = ImGui.getCursorScreenPos();
        boolean selected = project.equals(selectedProject);

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(pos.x, pos.y, pos.x + (ww * 0.8f), pos.y + 50, selected ? selectedColor.getImGuiCol() : 0);
    }

}
