package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.ImageData;
import com.trivia.client.utility.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;



public class ImageService implements Runnable {
    private List<ImageData> imageData;

    /**
     * Filter for usable images.
     */
    public ImageService(List<ImageData> imageData) {
        imageData = imageData.stream().filter(i -> !ImageUtil
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
