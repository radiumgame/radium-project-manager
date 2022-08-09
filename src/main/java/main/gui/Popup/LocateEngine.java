package main.gui.Popup;

import imgui.ImGui;
import imgui.app.Color;
import imgui.flag.ImGuiWindowFlags;
import main.FileExplorer;
import main.Settings;
import main.gui.GuiUtility;

public class LocateEngine extends GuiPopup {

    public LocateEngine() {
        super("Locate Engine", PopupType.Modal);
        flags = ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse;
    }

    @Override
    public void Render() {
        ImGui.setWindowSize(500, 300);
        Center();

        GuiUtility.InputString("##EnginePath", Settings.Instance.RadiumPath);
        ImGui.sameLine();
        if (ImGui.button("Choose")) {
            String path = FileExplorer.Choose("exe");
            if (FileExplorer.IsPathValid(path)) {
                Settings.Instance.RadiumPath = path;
            }
        }

        if (!Settings.IsEnginePathValid()) {
            GuiUtility.Text("Invalid engine path", Color.Red);
        }

        if (ImGui.button("Save")) {
            Settings.Instance.Save();
            ImGui.closeCurrentPopup();
        }
        ImGui.sameLine();
        if (ImGui.button("Cancel") && Settings.IsEnginePathValid()) {
            ImGui.closeCurrentPopup();
        }
    }

}
