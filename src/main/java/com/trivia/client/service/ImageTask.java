package com.trivia.client.service;

import com.trivia.client.utility.ImageUtil;

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
            .request("image/" + ImageUtil.IMAGE_FORMAT)
            .get();

        if (response.getStatusInfo().equals(Response.Status.OK)) {
            response.bufferEntity();
            InputStream imageStream = response.readEntity(InputStream.class);

            try {
                ImageUtil.saveImage(imageStream, imagePath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.close();
        return "";
    }
}
