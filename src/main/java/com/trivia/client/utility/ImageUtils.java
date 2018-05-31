package com.trivia.client.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ImageUtils {
    public static final Path IMAGE_DIR = Paths.get(System.getProperty("java.io.tmpdir") + "Trivia/images");
    public static final String IMAGE_FORMAT = "png";
    private static final int DIR_MAX_SIZE = 1024 * 1024 * 100; // 100 mb

    public static void saveImage(InputStream imageStream, String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageStream);
        ImageIO.write(bufferedImage, IMAGE_FORMAT,
            Paths.get(IMAGE_DIR + "/" + imagePath).toFile());
    }

    public static boolean isSaved(String imagePath) {
        return Files.exists(Paths.get(IMAGE_DIR + "/" + imagePath));
    }

    public static void checkForClean() {
        File[] files = IMAGE_DIR.toFile().listFiles();
        if (files == null) return;

        int size = 0;
        for (File file : files) {
            size += file.getTotalSpace();
        }

        if (size > DIR_MAX_SIZE) {
            Arrays.stream(files).forEach(f -> f.delete());
        }
    }
}
