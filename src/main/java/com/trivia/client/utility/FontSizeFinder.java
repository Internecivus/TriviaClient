package com.trivia.client.utility;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontSizeFinder {
    public static int findFor(Font font, String string, double maxWidth, double maxHeight, int maxRows) {
        double fontSize = font.getSize();
        double width = getTextWidth(font, string, maxWidth, maxHeight);
        if (width > maxWidth) {
            return (int) (fontSize * maxWidth / width);
        }
        return (int) fontSize;
    }

    private static double getTextWidth(Font font, String string, double maxWidth, double maxHeight) {
        Text text = new Text(string);
        text.setFont(font);
        text.setWrappingWidth(maxWidth);

        return text.getLayoutBounds().getWidth();
    }
}
