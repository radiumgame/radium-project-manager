package main;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.nfd.NativeFileDialog;

public class FileExplorer {

    protected FileExplorer() {}

    public static String Create(String extension) {
        PointerBuffer outPath = MemoryUtil.memAllocPointer(1);

        int res = NativeFileDialog.NFD_SaveDialog(extension, null, outPath);
        if (res != NativeFileDialog.NFD_OKAY) {
            MemoryUtil.memFree(outPath);
            return "";
        }

        String result;
        try {
            result = outPath.getStringUTF8();
            MemoryUtil.memFree(outPath);
        } catch (Exception e) {
            MemoryUtil.memFree(outPath);
            return "";
        }

        return result;
    }

    public static String Choose(String extensions) {
        PointerBuffer outPath = MemoryUtil.memAllocPointer(1);
        int res = NativeFileDialog.NFD_OpenDialog(extensions, null, outPath);
        if (res != NativeFileDialog.NFD_OKAY) {
            return "";
        }

        String result = "";
        try {
            result = outPath.getStringUTF8();
            MemoryUtil.memFree(outPath);
        } catch (Exception e) {
            MemoryUtil.memFree(outPath);
            return "";
        }

        return result;
    }

    public static String CreateDirectory() {
        PointerBuffer outPath = MemoryUtil.memAllocPointer(1);
        int res = NativeFileDialog.NFD_SaveDialog("", null, outPath);
        if (res != NativeFileDialog.NFD_OKAY) {
            return "";
        }

        String result;
        try {
            result = outPath.getStringUTF8();
            MemoryUtil.memFree(outPath);
        } catch (Exception e) {
            MemoryUtil.memFree(outPath);
            return "";
        }

        return result;
    }

    public static String ChooseDirectory() {
        PointerBuffer outPath = MemoryUtil.memAllocPointer(1);
        int res = NativeFileDialog.NFD_PickFolder("", outPath);
        if (res != NativeFileDialog.NFD_OKAY) {
            return "";
        }

        String result;
        try {
            result = outPath.getStringUTF8();
            MemoryUtil.memFree(outPath);
        } catch (Exception e) {
            MemoryUtil.memFree(outPath);
            return "";
        }

        return result;
    }

    public static boolean IsPathValid(String path) {
        return !path.equals("");
    }

}
