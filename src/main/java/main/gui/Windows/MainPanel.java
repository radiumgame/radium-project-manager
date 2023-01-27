package main.gui.Windows;

import imgui.ImGui;
import imgui.app.Color;
import imgui.flag.ImGuiCol;
import main.FileExplorer;
import main.Settings;
import main.Texture;
import main.Util.FileUtility;
import main.gui.Popup.GuiPopup;
import main.notification.ImNotification;
import main.notification.ImNotify;
//import org.eclipse.jgit.api.Git;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends GuiWindow {

    private final Color primaryColor = new Color(0.2784313725f, 0.462745098f, 0.6941176471f, 1);
    private final Color primaryColorHovered = new Color(0.2784313725f, 0.462745098f, 0.6941176471f, 0.85f);

    private int logo = -1;

    public MainPanel() {
        super("Create Project");
    }

    @Override
    public void Render() {
        if (logo == -1) logo = Texture.LoadTexture("assets/images/logo.png");

        float prevY = ImGui.getCursorScreenPosY();
        ImGui.image(logo, 300, 150);
        ImGui.setCursorScreenPos(ImGui.getCursorScreenPosX(), prevY);

        ImGui.setCursorScreenPos(ImGui.getWindowPosX() + (ImGui.getWindowWidth() - 420), ImGui.getCursorScreenPosY() + (ImGui.getWindowHeight() / 2) - 55);
        if (ImGui.button("Open Project", 200, 50)) {
            String project = FileExplorer.ChooseDirectory();
            if (FileExplorer.IsPathValid(project)) {
                OpenProject(project);
            }
        }

        ImGui.sameLine();
        ImGui.pushStyleColor(ImGuiCol.Button, primaryColor.getImGuiCol());
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, primaryColorHovered.getImGuiCol());
        if (ImGui.button("Create Project", 200, 50)) {
            GuiPopup.Open("Create Project Menu");
        }
        ImGui.popStyleColor(2);
        GuiPopup.Update("Create Project Menu");

        ImGui.setCursorScreenPos(ImGui.getCursorScreenPosX() + (ImGui.getWindowWidth() - 320), ImGui.getCursorScreenPosY() + 5);
        if (ImGui.button("Change Engine", 200, 50)) {
            GuiPopup.Open("Locate Engine");
        }
        GuiPopup.Update("Locate Engine");
    }

    public static void CreateProject(String root, String projectName) {
        try {
            if (Files.exists(Paths.get(root + "/" + projectName))) {
                return;
            }

            Path source = Paths.get("assets/sample-project/");
            Path destination = Paths.get(root + "/" + projectName);
            CopyFolder(source, destination);

            File project = new File(root + "/" + projectName);
            RenameFiles(project, projectName);

            Settings.AddRecentProject(project.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void CopyFolder(Path source, Path destination) throws Exception {
        CopyDirectory(new File(source.toString()), new File(destination.toString()));
    }

    private static void CopyDirectory(File sourceDirectory, File destinationDirectory) throws Exception {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            CopyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public static void CopyDirectoryCompatibityMode(File source, File destination) throws Exception {
        if (source.isDirectory()) {
            CopyDirectory(source, destination);
        } else {
            CopyFile(source, destination);
        }
    }

    private static void CopyFile(File sourceFile, File destinationFile)
            throws Exception {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }


    public static void OpenProject(String projectDirectory) {
        if (!Settings.Instance.currentEngine.valid) {
            ImNotify.notify(new ImNotification("No Engine", "There is no engine selected or installed", 4, ImNotification.NotificationType.Error));
            return;
        }

        try {
            String radiumPath = Settings.Instance.currentEngine.path;
            if (Settings.IsEnginePathValid()) {
                Runtime runtime = Runtime.getRuntime();
                String command = radiumPath + " " + "\"" + projectDirectory + "\"";
                runtime.exec(command);

                Settings.AddRecentProject(projectDirectory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void RenameFiles(File project, String projectName) {
        for (File file : project.listFiles()) {
            if (!file.isFile()) continue;

            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (fileName.contains("sample-project")) {
                file.renameTo(new File(project.getAbsolutePath() + "/" + projectName + extension));
            }

            File newFile = new File(project.getAbsolutePath() + "/" + projectName + extension);
            if (extension.equals(".config")) {
                String content = FileUtility.ReadFile(newFile);
                content = content.replace("sample-project", projectName);
                FileUtility.Write(newFile, content, false);
            }
        }
    }

    private static void DeleteDirectory(File file) throws Exception {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                DeleteDirectory(subfile);
            }

            if (!subfile.delete()) {
                throw new Exception("Failed to delete file: " + subfile.getAbsolutePath());
            }
        }
    }

}
