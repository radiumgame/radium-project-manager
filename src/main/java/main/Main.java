package main;

import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiConfigFlags;

public class Main extends Application {

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application");
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.getFonts().addFontDefault();
    }

    @Override
    public void process() {
        Dockspace.BeginDockspace();
        ImGui.begin("test");

        ImGui.end();
        Dockspace.EndDockspace();
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

}
