package com.trivia.client.service;

import com.trivia.client.utility.ImageUtils;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class ImageTask implements Callable<String> {
    private String imagePath;

    public ImageTask(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String call() {
        Response response = ClientManager
            .getServerTarget()
            .path("images/" + imagePath)
            .request("image/" + ImageUtils.IMAGE_FORMAT)
            .get();

        if (response.getStatusInfo().equals(Response.Status.OK)) {
            response.bufferEntity();
            InputStream imageStream = response.readEntity(InputStream.class);

            try {
                ImageUtils.saveImage(imageStream, imagePath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.close();
        return "";
    }
}
