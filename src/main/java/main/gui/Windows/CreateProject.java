package main.gui.Windows;

import imgui.ImGui;
import main.FileExplorer;
import main.Settings;
import main.gui.Popup.GuiPopup;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateProject extends GuiWindow {

    public CreateProject() {
        super("Create Project");
    }

    @Override
    public void Render() {
        if (ImGui.button("Create Project")) {
            GuiPopup.Open("Create Project Menu");
        }
        GuiPopup.Update("Create Project Menu");

        boolean projectSelected = RecentProjects.selectedProject != null;
        if (!projectSelected) ImGui.beginDisabled();
        if (ImGui.button("Open Project")) {
            OpenProject(RecentProjects.selectedProject);
        }
        if (!projectSelected) ImGui.endDisabled();
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

    private void OpenProject(String projectDirectory) {
        try {
            String radiumPath = Settings.Instance.RadiumPath;
            if (Settings.IsEnginePathValid()) {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(radiumPath + " " + projectDirectory);
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
