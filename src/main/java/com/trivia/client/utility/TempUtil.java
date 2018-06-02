package com.trivia.client.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TempUtil {
    public static Path TEMP_DIR = Paths.get(System.getProperty("java.io.tmpdir") + "Trivia");

    private TempUtil() {}

    public static void init() {
        checkExists();
        ImageUtil.checkForClean();
    }

    public static void checkExists() {
        try {
            if (!Files.exists(TEMP_DIR)) {
                Files.createDirectory(TEMP_DIR);
                Files.createDirectory(ImageUtil.IMAGE_DIR);
            }
        }
        catch (IOException e) {
            StageManager.saveAndExit();
        }
    }
}
