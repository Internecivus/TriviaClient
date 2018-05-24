package com.trivia.client.service;

import com.trivia.client.model.Category;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// TODO: Connect to server
// TODO: Register client here using server_id and secret using basic auth
// TODO: Get API key and secret and save it
public class ClientService extends Service<List<Category>> {
    @Override
    protected Task<List<Category>> createTask() {
        return new Task<>() {
            @Override
            protected List<Category> call() {

                Client client = ClientBuilder.newClient();
                WebTarget target = client.target(ClientManager.SERVER_API);

                Response response = target.path("/categories").request(MediaType.APPLICATION_JSON_TYPE).get();
                response.close();

                List<Category> categories = response.readEntity(new GenericType<List<Category>>() {});
                client.close();
                return categories;
            }
        };
    }
}