package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.Question;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuestionsService extends Service<List<Question>> {
    @Override
    protected Task<List<Question>> createTask() {
        return new Task<>() {
            @Override
            protected List<Question> call() {
                Response response = ClientManager
                    .getApiTarget()
                    .path("questions")
                    .queryParam("random", true)
                    .queryParam("size", GameManager.getGame().getGameDuration().getSize())
                    .matrixParam("category", GameManager.getGame().getCategory().getName())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                    .get();

                List<Question> questions = null;
                if (response.getStatusInfo().equals(Response.Status.OK)) {
                    response.bufferEntity();
                    questions = response.readEntity(new GenericType<List<Question>>() {});
                    GameManager.getGame().setQuestions(questions);

                    Thread thread = new Thread(new ImageService(GameManager.getGame().getAllImagePaths()));
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
                return questions;
            }
        };
    }
}