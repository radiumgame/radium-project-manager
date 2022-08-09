package imgui.app;

import imgui.ImColor;

import java.util.Arrays;

/**
 * Object for color data.
 */
public class Color implements Cloneable {

    public final float[] data = new float[4];

    public static final Color Red = new Color(1, 0, 0, 1);
    public static final Color Green = new Color(0, 1, 0, 1);
    public static final Color Blue = new Color(0, 0, 1, 1);
    public static final Color White = new Color(1, 1, 1, 1);
    public static final Color Black = new Color(0, 0, 0, 1);
    public static final Color Yellow = new Color(1, 1, 0, 1);
    public static final Color Magenta = new Color(1, 0, 1, 1);
    public static final Color Cyan = new Color(0, 1, 1, 1);

    public Color() {
    }

    public Color(final float[] color) {
        set(color);
    }

    public Color(final float red, final float green, final float blue, final float alpha) {
        data[0] = red;
        data[1] = green;
        data[2] = blue;
        data[3] = alpha;
    }

    public final void set(final float red, final float green, final float blue, final float alpha) {
        data[0] = red;
        data[1] = green;
        data[2] = blue;
        data[3] = alpha;
    }

    public final void set(final float[] color) {
        System.arraycopy(color, 0, data, 0, 4);
    }

    public final float getRed() {
        return data[0];
    }

    public final float getGreen() {
        return data[1];
    }

    public final float getBlue() {
        return data[2];
    }

    public final float getAlpha() {
        return data[3];
    }

    public final int getImGuiCol() {
        return ImColor.floatToColor(data[0], data[1], data[2], data[3]);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Color color = (Color) o;
        return Arrays.equals(data, color.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "Color{"
                + "data=" + Arrays.toString(data)
                + '}';
    }

    /**
     * Method to clone Color instance.
     *
     * @return cloned Color instance
     */
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Color clone() {
        return new Color(data);
    }
}