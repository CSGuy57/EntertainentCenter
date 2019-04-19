package org.pltw.examples.entertainmentcenter;

public class VideoGame extends Game {
    private String eSRBRating;
    private String platform;
    private double storageSize;

    public VideoGame(){

    }

    public VideoGame(String title, String description, int rating, int minPlayers, int maxPlayers, String eSRBRating, String platform, double storageSize) {
        super(title, description, rating, minPlayers, maxPlayers);
        this.eSRBRating = eSRBRating;
        this.platform = platform;
        this.storageSize = storageSize;
    }

// region Getters and Setters

    public String geteSRBRating() {
        return eSRBRating;
    }

    public void seteSRBRating(String eSRBRating) {
        this.eSRBRating = eSRBRating;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(double storageSize) {
        this.storageSize = storageSize;
    }

    // endregion
}
