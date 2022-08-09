package main;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiConfigFlags;
import main.gui.Dockspace;
import main.gui.Popup.GuiPopup;
import main.gui.Popup.LocateEngine;
import main.gui.Windows.CreateProject;
import main.gui.Windows.GuiWindow;
import main.gui.Themes.ModernDark;
import main.gui.Windows.RecentProjects;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private final List<GuiWindow> windows = new ArrayList<>();
    private Settings settings;

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Radium Project Manager");

        windows.add(new CreateProject());
        windows.add(new RecentProjects());

        new LocateEngine();

        settings = Settings.Load();
        if (!Files.exists(Paths.get(Settings.SETTINGS_FILE))) {
            settings.Save();
        }
        settings.Verify();
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename("assets/layout.ini");
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.getFonts().addFontFromFileTTF("assets/PTSans/PTSans-Regular.ttf", 18);

        config.setTheme(new ModernDark());
    }

    @Override
    public void process() {
        Dockspace.BeginDockspace();

        windows.forEach(GuiWindow::Update);
        if (!Settings.IsEnginePathValid()) {
            GuiPopup.Open("Locate Engine");
        }
        GuiPopup.Update("Locate Engine");

        Dockspace.EndDockspace();
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

}
