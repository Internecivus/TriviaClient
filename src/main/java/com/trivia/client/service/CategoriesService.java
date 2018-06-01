package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.Category;
import com.trivia.client.model.ImageData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class CategoriesService extends Service<List<Category>> {
    @Override
    protected Task<List<Category>> createTask() {
        return new Task<>() {
            @Override
            protected List<Category> call() {
                Response response = ClientManager
                    .getApiTarget()
                    .path("categories")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

                List<Category> categories = null;
                if (response.getStatusInfo().equals(Response.Status.OK)) {
                    response.bufferEntity();
                    categories = response.readEntity(new GenericType<List<Category>>() {});

                    List<ImageData> imagesData = categories
                        .stream().map(c -> c.getImageData()).filter(c -> Objects.nonNull(c)).collect(Collectors.toList());

                    Thread thread = new Thread(new ImageService(imagesData));
                    thread.run();
                    try {
                        thread.join();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new ServerConnectionException();
                    }
                }
                response.close();
                return categories;
            }
        };
    }
}