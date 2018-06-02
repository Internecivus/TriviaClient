package com.trivia.client.utility;

import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FontSizeFinder {
    /**
     *
     * @param font The font to be used for the calculation.
     * @param textString The text to be used for the calculation.
     * @param maxWidth The width that the text needs to fit in.
     * @param maxRows The maximum amount of rows that the text can have.
     * @return Font size that fits all the criteria.
     */
    public static int findFor(Font font, String textString, double maxWidth, int maxRows) {
        double fontSize = font.getSize();
        double maxHeight = getMaxTextHeight(font, maxRows);
        while (getTextHeight(font, textString, maxWidth) > maxHeight) {
            font = new Font(font.getName(), --fontSize);
        }
        return (int) fontSize;
    }

    /**
     * We get the height of one row of test text of the provided font and calculate the max height based
     * on the number of rows we want.
     */
    private static double getMaxTextHeight(Font font, int maxRows) {
        Text text = new Text("test");
        text.setFont(font);
        double oneRowHeight = text.getLayoutBounds().getHeight();
        return oneRowHeight * maxRows;
    }

    /**
     * We get the height of the provided text while using the appropriate width maximum.
     */
    private static double getTextHeight(Font font, String textString, double maxWidth) {
        Text text = new Text(textString);
        text.setFont(font);
        text.setWrappingWidth(maxWidth);
        return text.getLayoutBounds().getHeight();
    }
}
