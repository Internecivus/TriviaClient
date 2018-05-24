package com.trivia.client.service;

import com.trivia.client.model.Question;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientManager {
    public final static String SERVER_URI = "localhost:8080";
    public final static String SERVER_API = SERVER_URI + "/api";
    private final String PROVIDER_KEY = "555555555";
    private final String PROVIDER_SECRET = "5555555555";
    private final String REGISTER_URI = "/register";
    private String API_KEY;
    private String API_SECRET;


//    public void registerClient() {
//        WebTarget target = client.target(ClientManager.CLIENT_URI);
//
//
//
//
//        Response response = target.path("/register").request(MediaType.APPLICATION_JSON_TYPE).post();
//        response.close();
//
//        Question question = response.readEntity(Question.class);

  //  prefs = Preferences.userRoot().node(this.getClass().getName());
//
//        client.close();
//    }
//
//    public void createClient() {
//        Client triviaApiClient = ClientBuilder.newBuilder()
//                .property("connection.timeout", 100)
//                //.sslContext(sslContext)
//                //.register(JacksonJso.class)
//                .build()
//        Client client = ClientBuilder.newClient();
//
//
//
//        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().build();
//        client.register(feature);
//        client.register(SseFeature.class);
//        WebTarget target = client.target(baseurl + "/v1/devices/events/")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "...")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "...");
//
//
//                .header("Authorization", authHeader)
//
//        String authHeader = java.util.Base64.getEncoder().encodeToString((API_KEY + ":" + API_SECRET).getBytes());
//
//
//        triviaApiClient  = triviaApiClient
//                .target(SERVER_URI)
//                .path("public/codingmarks")
//                .queryParam("location", location)
//                .request(MediaType.APPLICATION_JSON)
//
//                .get();
//    }
}