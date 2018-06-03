package com.trivia.client.service;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.prefs.Preferences;



public class ClientManager {
    public final static String SERVER_HOST = "http://18.220.145.50";
    public final static String SERVER_PORT = "8080";
    public final static String SERVER_SOCKET = SERVER_HOST + ":" + SERVER_PORT;
    public final static String API_PATH =  "api";

    /**
     * {@link ClientManager#PROVIDER_KEY} and {@link ClientManager#PROVIDER_SECRET}
     * are the values that every client application provider has to provide in order for the client
     * to be able to register itself. Hardcoding it is not perfect but it is enough for now.
     */
    private final static String PROVIDER_KEY = "ZRTW4nVBGBfks6Df9LjRwBMFhrPMQw04";
    private final static String PROVIDER_SECRET = "4tojgeiJsiqUWGAOF9JwwtekZ1tzzfJk";

    private static String API_KEY = null;
    private static String API_SECRET = null;

    private static Client client;
    private static Preferences clientPreferences = Preferences.userRoot().node("Trivia").node("client");

    public static void init() {
        client = ClientBuilder.newClient();

        API_KEY = clientPreferences.get("API_KEY", null);
        API_SECRET = clientPreferences.get("API_SECRET", null);

        if (API_KEY == null || API_SECRET == null) {
            register();
        }
    }

    public static WebTarget getApiTarget() {
        client = setAuthorization(client, API_KEY, API_SECRET);

        WebTarget target;
        target = client.target(SERVER_SOCKET).path(API_PATH);
        return target;
    }

    public static WebTarget getServerTarget() {
        WebTarget target;
        target = client.target(SERVER_SOCKET);
        return target;
    }

    private static Client setAuthorization(Client client, String username, String password) {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        client.register(feature);
        return client;
    }

    public static void close() {
        client.close();
    }

    public static void restart() {
        close();
        start();
    }

    public static void start() {
        client = ClientBuilder.newClient();
    }

    private static void register() {
        client = setAuthorization(client, PROVIDER_KEY, PROVIDER_SECRET);
        WebTarget target;
        target = client.target(SERVER_SOCKET).path(API_PATH);
        Response response = target
            .path("client")
            .queryParam("register", true)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(null);

        if (response.getStatusInfo().equals(Response.Status.CREATED)) {
            parseAndSaveAuthorization(response.getHeaderString(HttpHeaders.AUTHORIZATION));
        }
        else {
            throw new IllegalStateException();
        }
        response.close();
        restart();
    }

    private static void parseAndSaveAuthorization(String rawHeader) {
        if (rawHeader != null && rawHeader.startsWith("Basic ")) {
            String[] headerCredential = new String(
                Base64.getDecoder().decode(rawHeader.substring("Basic ".length()))).split(":");
            API_KEY = headerCredential[0];
            API_SECRET = headerCredential[1];
            saveAuthorization();
        }
        else {
            throw new IllegalStateException();
        }
    }

    private static void saveAuthorization() {
        clientPreferences.put("API_KEY", API_KEY);
        clientPreferences.put("API_SECRET", API_SECRET);
    }

    public static Client getClient() {
        return client;
    }
}