package org.pltw.examples.entertainmentcenter;

public abstract class Game extends Entertainment {
    private int minPlayers;
    private int maxPlayers;

    public Game() {
        super();
    }

    public Game(String title, String description, int rating, int minPlayers, int maxPlayers) {
        super(title, description, rating);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    // region Getters and Setters

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    // endregion
}
