package main.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileUtility {

    protected FileUtility() {}

    /**
     * Load file contents to string
     * @param path File path
     * @return File contents
     */
    public static String LoadAsString(String path) {
        StringBuilder result = new StringBuilder();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                result.append(myReader.nextLine() + '\n');
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * Load file contents to string
     * @param f File
     * @return File contents
     */
    public static String ReadFile(File f) {
        try {
            String result = "";
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads file contents without the use of \n
     * @param f
     * @return File contents
     */
    public static String ReadRaw(File f) {
        try {
            String result = "";
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writes content to a file
     * @param file File to write
     * @param text New file content
     */
    public static void Write(File file, String text, boolean append) {
        try {
            FileWriter writer = new FileWriter(file, append);

            writer.write(text);

            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Create(String path) {
        try {
            Files.createFile(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
