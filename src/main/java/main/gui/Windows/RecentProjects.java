package main.gui.Windows;

import imgui.ImGui;
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
        float ww = ImGui.getWindowWidth();
        float wh = ImGui.getWindowHeight();
        if (ImGui.beginListBox("Recent Projects", ww * 0.7f, wh * 0.9f)) {
            List<String> projects = Settings.Instance.RecentProjects;
            for (String project : projects) {
                boolean selected = project.equals(selectedProject);

                if (selected) {
                    ImGui.pushStyleColor(ImGuiCol.Header, selectedColor.getImGuiCol());
                    ImGui.pushStyleColor(ImGuiCol.HeaderHovered, selectedColor.getImGuiCol());
                    ImGui.pushStyleColor(ImGuiCol.HeaderActive, selectedColor.getImGuiCol());
                }
                if (ImGui.selectable(project, selected)) {
                    selectedProject = project;
                }
                if (selected) {
                    ImGui.popStyleColor(3);
                }
            }

            ImGui.endListBox();
        }
    }

}
