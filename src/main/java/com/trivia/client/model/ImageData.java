package com.trivia.client.model;

import java.util.Date;

public class ImageData {
    private String path;
    private Date dateCreated;

    public ImageData() {}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
