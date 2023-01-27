package main.engine;

import main.FileExplorer;
import main.Settings;
import main.notification.ImNotification;
import main.notification.ImNotify;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Installer {

    public static EngineInstall InstallEngine(String version) {
        for (EngineInstall eng : Settings.Instance.Engines) {
            if (eng.version.equals(version)) {
                ImNotify.notify(new ImNotification("Installation", "This version is already located on this system.", 4, ImNotification.NotificationType.Error));
                return new EngineInstall();
            }
        }

        if (!ConnectedToInternet()) {
            ImNotify.notify(new ImNotification("Connection", "Please establish an internet connection before continuing", 4, ImNotification.NotificationType.Error));
            return new EngineInstall();
        }

        String path = FileExplorer.ChooseDirectory();
        if (!FileExplorer.IsPathValid(path)) {
            return new EngineInstall();
        }

        try (BufferedInputStream in = new BufferedInputStream(new URL(GetEngineWebPath(version)).openStream())) {
            String finalPath = path + "/" + version + ".zip";
            Files.createFile(Paths.get(finalPath));
            FileOutputStream fileOutputStream = new FileOutputStream(finalPath);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            in.close();
            fileOutputStream.close();

            Extract(new File(finalPath), version);
            Files.deleteIfExists(Paths.get(finalPath));

            return new EngineInstall(path + "/" + version + "/" + "Radium.exe", version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EngineInstall();
    }

    private static void Extract(File file, String version) throws Exception {
        if (!file.getName().endsWith(".zip")) {
            return;
        }

        Path dest = Files.createDirectories(Paths.get(file.getParentFile().getAbsolutePath() + "/" + version + "/"));
        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP);
        archiver.extract(file, dest.toFile());

        file.delete();
    }

    private static boolean ConnectedToInternet() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private static String GetEngineWebPath(String version) {
        return "https://github.com/radiumgame/radium/releases/download/" + version + "/Radium.zip";
    }

}
