package main.gui.Windows;

import imgui.ImGui;
import imgui.app.Color;
import imgui.flag.ImGuiCol;
import main.FileExplorer;
import main.Settings;
import main.Texture;
import main.gui.Popup.GuiPopup;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            Files.createDirectories(Paths.get(root + "/" + projectName));
            Git git = Git.cloneRepository()
                    .setURI("https://github.com/radiumgame/sample-project.git")
                    .setDirectory(new File(root + "/" + projectName))
                    .call();
            git.close();

            File project = new File(root + "/" + projectName);
            File gitDirectory = git.getRepository().getDirectory();
            DeleteDirectory(gitDirectory);
            gitDirectory.delete();
            RenameFiles(project, projectName);

            Settings.AddRecentProject(project.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OpenProject(String projectDirectory) {
        try {
            String radiumPath = Settings.Instance.RadiumPath;
            if (Settings.IsEnginePathValid()) {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(radiumPath + " " + projectDirectory);

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
