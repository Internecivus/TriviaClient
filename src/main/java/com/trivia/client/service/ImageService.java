package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.Category;
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
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class ImageService implements Runnable {
    private List<String> imagePaths;

    public ImageService(List<String> imagePaths) {
        imagePaths = imagePaths.stream().filter(i -> !ImageUtils.isSaved(i)).collect(Collectors.toList());
        this.imagePaths = imagePaths;
    }

    @Override
    public void run() {
        if (imagePaths.size() == 0) return;
        ExecutorService executor = Executors.newFixedThreadPool(imagePaths.size());
        List<Callable<String>> imageTasks = new ArrayList<>();
        for (String imagePath : imagePaths) {
            imageTasks.add(new ImageTask(imagePath));
        }
        try {
            executor.invokeAll(imageTasks);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            throw new ServerConnectionException();
        }
    }
}
