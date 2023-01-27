package main.engine;

public class EngineInstall {

    public String path;
    public String version;
    public boolean valid = true;

    public EngineInstall() {
        valid = false;
    }

    public EngineInstall(String path, String version) {
        this.path = path;
        this.version = version;
    }

}
