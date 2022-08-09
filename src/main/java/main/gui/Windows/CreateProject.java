package main.gui.Windows;

import imgui.ImGui;
import main.FileExplorer;
import main.gui.Popup.GuiPopup;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateProject extends GuiWindow {

    public CreateProject() {
        super("Create Project");
    }

    @Override
    public void Render() {
        if (ImGui.button("Create Project")) {
            String projectName = "test";
            String path = FileExplorer.ChooseDirectory();
            if (!FileExplorer.IsPathValid(path)) {
                return;
            }

            CreateProject(path, projectName);
        }
    }

    private void CreateProject(String root, String projectName) {
        try {
            Files.createDirectories(Paths.get(root + "/" + projectName));
            Git git = Git.cloneRepository()
                    .setURI("https://github.com/radiumgame/sample-project.git")
                    .setDirectory(new File(root + "/" + projectName))
                    .call();
            git.close();

            File project = new File(root + "/sample-project");
            File gitDirectory = git.getRepository().getDirectory();
            DeleteDirectory(gitDirectory);
            gitDirectory.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DeleteDirectory(File file) throws Exception {
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
