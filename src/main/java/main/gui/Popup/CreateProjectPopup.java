package main.gui.Popup;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import main.FileExplorer;
import main.Settings;
import main.gui.GuiUtility;
import main.gui.Windows.MainPanel;

public class CreateProjectPopup extends GuiPopup {

    private String projectName = "NewProject";

    public CreateProjectPopup() {
        super("Create Project Menu", PopupType.Modal);
        flags = ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
    }

    @Override
    public void Render() {
        ImGui.setWindowSize(500, 300);
        Center();

        Settings.Instance.LastCreatePath = GuiUtility.InputString("Project Path", Settings.Instance.LastCreatePath);
        ImGui.sameLine();
        if (ImGui.button("Choose")) {
            String res = FileExplorer.ChooseDirectory();
            if (FileExplorer.IsPathValid(res)) {
                Settings.Instance.LastCreatePath = res;
            }
        }

        projectName = GuiUtility.InputString("Project Name", projectName);
        if (ImGui.button("Create Project")) {
            projectName = projectName.replaceAll(" ", "-");
            MainPanel.CreateProject(Settings.Instance.LastCreatePath, projectName);
            Settings.Instance.Save();
            ImGui.closeCurrentPopup();
        }
        ImGui.sameLine();
        if (ImGui.button("Cancel")) {
            ImGui.closeCurrentPopup();
        }
    }

}
