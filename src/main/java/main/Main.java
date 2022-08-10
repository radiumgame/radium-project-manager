package main;

import imgui.ImFont;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiConfigFlags;
import main.gui.Dockspace;
import main.gui.Popup.CreateProjectPopup;
import main.gui.Popup.GuiPopup;
import main.gui.Popup.LocateEngine;
import main.gui.Windows.*;
import main.gui.Themes.ModernDark;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    private final List<GuiWindow> windows = new ArrayList<>();
    private Settings settings;

    public static final HashMap<String, ImFont> fonts = new HashMap<>();

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Radium Project Manager");

        windows.add(new MainPanel());
        windows.add(new RecentProjects());
        windows.add(new Divider());
        //windows.add(new Sidepanel());

        new LocateEngine();
        new CreateProjectPopup();

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
        addFont("regular", 18, FontType.Regular);
        addFont("large", 24, FontType.Regular);
        addFont("large bold", 24, FontType.Bold);
        addFont("small", 14, FontType.Regular);

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

    private static ImFont addFont(String name, int size, FontType ft) {
        if (fonts.containsKey(name)) {
            return fonts.get(name);
        }

        String defaultPath = "assets/PTSans/PTSans-";
        ImFont font = ImGui.getIO().getFonts().addFontFromFileTTF(defaultPath + ft.name() + ".ttf", size);
        fonts.put(name, font);
        return font;
    }

    public static ImFont getFont(String name) {
        return fonts.getOrDefault(name, ImGui.getFont());
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

    public static enum FontType {

        Regular,
        Bold,
        Italic

    }

}
