package org.pltw.examples.entertainmentcenter;

import java.io.Serializable;

public abstract class Entertainment implements Serializable {
    private String title;
    private String description;
    private float rating;
    private String objectId;

    public Entertainment() {
    }

    public Entertainment(String title, String description, int rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    // region Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    // endregion

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
