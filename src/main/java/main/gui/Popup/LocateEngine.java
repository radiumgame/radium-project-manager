package main.gui.Popup;

import imgui.ImColor;
import imgui.ImGui;
import imgui.app.Color;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import main.FileExplorer;
import main.Settings;
import main.engine.EngineInstall;
import main.engine.Installer;
import main.gui.GuiUtility;
import main.notification.ImNotification;
import main.notification.ImNotify;

public class LocateEngine extends GuiPopup {

    private EngineInstall selectedEngine;
    private boolean installScreen = false;

    private final int VersionFlags = ImGuiTreeNodeFlags.SpanAvailWidth | ImGuiTreeNodeFlags.Leaf | ImGuiTreeNodeFlags.FramePadding;
    private final int SelectedColor = ImColor.floatToColor(11f / 255f, 90f / 255f, 113f / 255f, 1f);

    public LocateEngine() {
        super("Locate Engine", PopupType.Standard);
        flags = ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse;
    }

    @Override
    public void Render() {
        if (selectedEngine == null && Settings.Instance.engines.length > 0) {
            selectedEngine = Settings.Instance.engines[0];
        }

        ImGui.setWindowSize(500, 300);
        Center();

        if (!installScreen) {
            Chooser();
        } else {
            InstallScreen();
        }
    }

    private void Chooser() {
        ImGui.beginChildFrame("EngineInstall".hashCode(), 500, 300);
        for (EngineInstall engine : Settings.Instance.Engines) {
            boolean selected = engine.equals(selectedEngine);
            if (selected) {
                ImGui.pushStyleColor(ImGuiCol.Header, SelectedColor);
                ImGui.pushStyleColor(ImGuiCol.HeaderActive, SelectedColor);
            }
            ImGui.pushStyleColor(ImGuiCol.HeaderHovered, SelectedColor);
            ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, 5);

            if (ImGui.treeNodeEx(engine.version, VersionFlags | (selected ? ImGuiTreeNodeFlags.Selected : 0))) {
                ImGui.treePop();
            }

            if (ImGui.isItemClicked()) {
                selectedEngine = engine;
            }

            if (selected) {
                ImGui.popStyleColor(2);
            }
            ImGui.popStyleColor(1);
            ImGui.popStyleVar();
        }
        ImGui.endChildFrame();

        if (Settings.Instance.engines.length == 0) {
            ImGui.textColored(ImColor.floatToColor(1, 0, 0), "An engine must be installed to continue.");
        }

        if (ImGui.button("Install Engine")) {
            installScreen = true;
        }
        ImGui.sameLine();

        if (selectedEngine != null) {
            if (ImGui.button("Select")) {
                Settings.Instance.currentEngine = selectedEngine;
                ImGui.closeCurrentPopup();
            }
        } else {
            if (ImGui.button("Close")) {
                ImGui.closeCurrentPopup();
            }
        }
    }

    private final String[] availableVersions = {
      "v1.0.5",
    };
    private int selectedVersion = availableVersions.length - 1;
    private void InstallScreen() {
        selectedVersion = GuiUtility.Dropdown("Editor Version", selectedVersion, availableVersions);

        if (ImGui.button("Install Engine")) {
            EngineInstall newEngine = Installer.InstallEngine(availableVersions[selectedVersion]);
            if (newEngine.valid) {
                Settings.AddEngine(newEngine, true);
                ImGui.closeCurrentPopup();
            } else {
                ImNotify.notify(new ImNotification("Installation Failure", "Your engine has failed to download, look for other messages to clue in why", 4, ImNotification.NotificationType.Error));
            }
        }
        ImGui.sameLine();
        if (ImGui.button("Choose Engine")) {
            installScreen = false;
        }
    }

}
