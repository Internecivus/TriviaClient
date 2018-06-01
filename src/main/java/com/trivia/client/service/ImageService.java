package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.Category;
import com.trivia.client.model.ImageData;
import com.trivia.client.service.ClientManager;
import com.trivia.client.service.GameManager;
import com.trivia.client.service.ImageTask;
import com.trivia.client.utility.ImageUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;



public class ImageService implements Runnable {
    private List<ImageData> imageData;

    /**
     * Filter for usable images.
     */
    public ImageService(List<ImageData> imageData) {
        imageData = imageData.stream().filter(i -> !ImageUtils
            .isUsable(i)).collect(Collectors.toList());
        this.imageData = imageData;
    }

    @Override
    public void run() {
        if (imageData == null || imageData.size() == 0) return;

        // Exact thread pool size is still in question.
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<String>> imageTasks = new ArrayList<>();
        imageData.forEach(i -> imageTasks.add(new ImageTask(i.getPath())));
        try {
            executor.invokeAll(imageTasks);
            executor.shutdown();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            throw new ServerConnectionException();
        }
    }
}
