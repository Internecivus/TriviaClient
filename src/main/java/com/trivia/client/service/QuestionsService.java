package com.trivia.client.service;

import com.trivia.client.exception.ServerConnectionException;
import com.trivia.client.model.ImageData;
import com.trivia.client.model.Question;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

                if (response.getStatusInfo().equals(Response.Status.OK)) {
                    response.bufferEntity();
                    List<Question> questions = response.readEntity(new GenericType<List<Question>>() {});
                    GameManager.getGame().setQuestions(questions);
                    List<ImageData> imageDatas = GameManager.getGame().getQuestionsImageData();

                    Thread thread = new Thread(new ImageService(imageDatas));
                    thread.run();
                    try {
                        thread.join();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new ServerConnectionException();
                    }
                    finally {
                        response.close();
                    }

                    return questions;
                }
                else {
                    response.close();
                    throw new IllegalStateException(response.getStatusInfo().toString() + " " + response.getEntity().toString());
                }
            }
        };
    }
}