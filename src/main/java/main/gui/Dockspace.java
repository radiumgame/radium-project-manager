package main.gui;

import imgui.ImColor;
import imgui.ImGuiWindowClass;
import imgui.app.Application;
import imgui.app.Window;
import imgui.flag.*;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;
import org.lwjgl.glfw.GLFW;

import java.nio.IntBuffer;

public class Dockspace {

    public static void BeginDockspace() {
        int windowFlags = ImGuiWindowFlags.NoDocking;
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.setNextWindowSize(Window.Width, Window.Height);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
        windowFlags |= ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin(" ", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        ImGui.pushStyleColor(ImGuiCol.DockingEmptyBg, 0);
        ImGui.dockSpace(ImGui.getID("Dockspace"));
        ImGui.popStyleColor();
    }

    public static void EndDockspace() {
        ImGui.end();
    }

}
