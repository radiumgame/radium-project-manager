package main.gui;

import imgui.ImGui;
import imgui.app.Color;
import imgui.type.ImInt;
import imgui.type.ImString;

public class GuiUtility {

    protected GuiUtility() {}

    public static void Text(String content) {
        ImGui.text(content);
    }

    public static void Text(String content, Color color) {
        ImGui.textColored(color.getImGuiCol(), content);
    }

    public static boolean Button(String content) {
        return ImGui.button(content);
    }

    public static int Dropdown(String label, int displayValue, String[] displayEnum) {
        ImInt val = new ImInt(displayValue);
        if (ImGui.combo(label, val, displayEnum, displayEnum.length)) {
            return val.get();
        }

        return displayValue;
    }

    public static int DragInt(String label, int displayValue) {
        int[] imInt = { displayValue };
        if (ImGui.dragInt(label, imInt)) {
            return imInt[0];
        }

        return displayValue;
    }

    public static int DragInt(String label, int displayValue, float width) {
        int[] imInt = { displayValue };
        ImGui.setNextItemWidth(width);
        if (ImGui.dragInt(label, imInt)) {
            return imInt[0];
        }

        return displayValue;
    }

    public static float DragFloat(String label, float displayValue) {
        float newFloat = displayValue;

        float[] imFloat = { displayValue };
        if (ImGui.dragFloat(label, imFloat)) {
            newFloat = imFloat[0];
        }

        return newFloat;
    }

    public static int SliderInt(String label, int displayValue, int min, int max) {
        int newInt = displayValue;

        int[] imInt = { displayValue };
        if (ImGui.sliderInt(label, imInt, min, max)) {
            newInt = imInt[0];
        }

        return newInt;
    }

    public static float SliderFloat(String label, float displayValue, float min, float max) {
        float newFloat = displayValue;

        float[] imFloat = { displayValue };
        if (ImGui.sliderFloat(label, imFloat, min, max)) {
            newFloat = imFloat[0];
        }

        return newFloat;
    }

    public static boolean Checkbox(String label, boolean displayValue) {
        boolean newBoolean = displayValue;

        if (ImGui.checkbox(label, displayValue)) {
            newBoolean = !displayValue;
        }

        return newBoolean;
    }

    public static String InputString(String label, String displayValue) {
        String newString = displayValue;

        ImString outString = new ImString(displayValue, 256);
        if (ImGui.inputText(label, outString)) {
            newString = outString.get();
        }

        return newString;
    }

    public static Color ColorField(String label, Color displayColor) {
        ImGui.colorEdit4(label, displayColor.data);
        return displayColor;
    }

    public static Color ColorField(String label, Color displayColor, int flags) {
        ImGui.colorEdit4(label, displayColor.data, flags);
        return displayColor;
    }

}
