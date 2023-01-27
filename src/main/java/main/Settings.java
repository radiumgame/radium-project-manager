package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.engine.EngineInstall;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Settings {

    public static final String SETTINGS_FILE = "assets/settings.json";
    public static Settings Instance;

    public EngineInstall currentEngine;
    @JsonIgnore  public List<String> RecentProjects;
    public String[] recentProjectArray;
    public String LastCreatePath;
    @JsonIgnore public List<EngineInstall> Engines;
    public EngineInstall[] engines;

    public Settings() {}

    public Settings(EngineInstall engineInstall, EngineInstall[] engines, String[] recentProjects, String lastCreatePath) {
        this.currentEngine = engineInstall;
        this.engines =  engines;
        this.Engines = List.of(engines);
        this.recentProjectArray = recentProjects;
        this.RecentProjects = List.of(recentProjects);
        this.LastCreatePath = lastCreatePath;
        this.engines = new EngineInstall[0];
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
        if (Engines == null) {
            Engines = new ArrayList<>();
            if (engines != null) {
                Collections.addAll(Engines, engines);
            }
        }
        if (RecentProjects == null) {
            RecentProjects = new ArrayList<>();
            if (recentProjectArray != null) {
                Collections.addAll(RecentProjects, recentProjectArray);
            }
        }

        if (currentEngine == null) currentEngine = new EngineInstall();

        RecentProjects.removeIf(path -> !Files.exists(Paths.get(path)));
        Engines.removeIf(engine -> !Files.exists(Paths.get(engine.path)));

        Instance.engines = Instance.Engines.toArray(new EngineInstall[0]);
        Instance.recentProjectArray = Instance.RecentProjects.toArray(new String[0]);

        Save();

        if (LastCreatePath == null) LastCreatePath = "";

        Save();
    }

    public static Settings Load() {
        try {
            Path path = Paths.get(SETTINGS_FILE);
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path, "{\"currentEngine\":{\"path\":null,\"version\":null,\"valid\":false},\"engines\":[],\"recentProjectArray\":[],\"LastCreatePath\":\"\"}");
            }

            String fileData = new String(Files.readAllBytes(path));
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(fileData, Settings.class);
            Instance = settings;

            settings.RecentProjects = new ArrayList<>();
            Collections.addAll(settings.RecentProjects, settings.recentProjectArray);

            settings.Engines = new ArrayList<>();
            Collections.addAll(settings.Engines, settings.engines);

            String currentEnginePath = settings.currentEngine.path;
            if (currentEnginePath == null || !new File(currentEnginePath).exists()) {
                settings.currentEngine = new EngineInstall();
            }

            return settings;
        } catch (Exception e) {
            e.printStackTrace();
            Instance = new Settings(new EngineInstall(), new EngineInstall[0], new String[0], "");
            return Instance;
        }
    }

    public static boolean IsEnginePathValid() {
        return Instance.currentEngine != null && Instance.currentEngine.path != null && !Instance.currentEngine.path.equals("") && Files.exists(Paths.get(Instance.currentEngine.path));
    }

    public static void AddRecentProject(String recentProject) {
        if (Instance.RecentProjects.contains(recentProject)) return;

        Instance.Verify();
        Instance.RecentProjects.add(recentProject);
        Instance.recentProjectArray = Instance.RecentProjects.toArray(new String[0]);

        Instance.Save();
    }

    public static void AddEngine(EngineInstall engine, boolean setCurrent) {
        if (Instance.Engines.contains(engine)) return;

        if (setCurrent) {
            Instance.currentEngine = engine;
        }

        Instance.Verify();
        Instance.Engines.add(engine);
        Instance.engines = Instance.Engines.toArray(new EngineInstall[0]);

        Instance.Save();
    }

}
