package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Settings {

    public static final String SETTINGS_FILE = "assets/settings.json";
    public static Settings Instance;

    public String RadiumPath;
    public List<String> RecentProjects;

    public Settings() {}

    public Settings(String radiumPath, String[] recentProjects) {
        this.RadiumPath = radiumPath;
        this.RecentProjects = List.of(recentProjects);
    }

    public void Save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(SETTINGS_FILE), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Verify() {
        if (RecentProjects == null) {
            RecentProjects = new ArrayList<>();
        }

        RecentProjects.removeIf(path -> !Files.exists(Paths.get(path)));
        Save();
    }

    public static Settings Load() {
        try {
            String fileData = new String(Files.readAllBytes(new File(SETTINGS_FILE).toPath()));
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(fileData, Settings.class);
            Instance = settings;

            return settings;
        } catch (Exception e) {
            e.printStackTrace();
            Instance = new Settings("", new String[0]);
            return Instance;
        }
    }

    public static boolean IsEnginePathValid() {
        return !Instance.RadiumPath.equals("") && Files.exists(Paths.get(Instance.RadiumPath));
    }

    public static void AddRecentProject(String recentProject) {
        Instance.Verify();
        Instance.RecentProjects.add(recentProject);

        Instance.Save();
    }

}
