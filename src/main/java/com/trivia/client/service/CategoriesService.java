package com.trivia.client.service;

import com.trivia.client.exception.Alerts;
import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.Category;
import com.trivia.client.model.Question;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.net.URI;
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
                    List<String> imagePaths = categories
                        .stream().map(q -> q.getImage()).filter(g -> Objects.nonNull(g)).collect(Collectors.toList());

                    Thread thread = new Thread(new ImageService(imagePaths));
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