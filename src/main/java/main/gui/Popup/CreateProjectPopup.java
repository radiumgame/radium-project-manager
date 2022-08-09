package main.gui.Popup;

import imgui.ImGui;
import main.FileExplorer;
import main.gui.GuiUtility;
import main.gui.Windows.CreateProject;

public class CreateProjectPopup extends GuiPopup {

    private String projectPath = "";
    private String projectName = "New Project";

    public CreateProjectPopup() {
        super("Create Project Menu", PopupType.Modal);
    }

    @Override
    public void Render() {
        ImGui.setWindowSize(500, 300);
        Center();

        projectPath = GuiUtility.InputString("Project Path", projectPath);
        ImGui.sameLine();
        if (ImGui.button("Choose")) {
            String res = FileExplorer.ChooseDirectory();
            if (FileExplorer.IsPathValid(res)) {
                projectPath = res;
            }
        }

        projectName = GuiUtility.InputString("Project Name", projectName);
        if (ImGui.button("Create Project")) {
            CreateProject.CreateProject(projectPath, projectName);
            ImGui.closeCurrentPopup();
        }
    }

}
