package com.trivia.client.utility;

import com.trivia.client.model.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;

public class ImageUtil {
    public static final Path IMAGE_DIR = Paths.get(TempUtil.TEMP_DIR + "/images");
    public static final String IMAGE_FORMAT = "png";
    private static final int DIR_MAX_SIZE = 1024 * 1024 * 100; // 100 mb

    private ImageUtil() {}

    public static void saveImage(InputStream imageStream, String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageStream);
        ImageIO.write(bufferedImage, IMAGE_FORMAT,
            Paths.get(IMAGE_DIR + "/" + imagePath).toFile());
    }

    /**
     * Checks if image is a) already saved and b) needs to be replaced with a newer version.
     */
    public static boolean isUsable(ImageData imageData) {
        try {
            if (isSaved(imageData.getPath())) {
                return (!needsUpdate(imageData));
            }
        }
        // Should be impossible to happen.
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean needsUpdate(ImageData imageData) throws IOException {
        BasicFileAttributes tmpAttributes = Files.readAttributes(
            Paths.get(IMAGE_DIR + "/" + imageData.getPath()), BasicFileAttributes.class
        );

        // Compare the server image creation date with the tmp creation image date.
        return (imageData.getDateCreated().after(new Date(tmpAttributes.creationTime().toMillis())));
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
