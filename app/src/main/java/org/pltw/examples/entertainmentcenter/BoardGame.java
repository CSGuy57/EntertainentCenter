package org.pltw.examples.entertainmentcenter;

public class BoardGame extends Game {
    private int playTime;

    public BoardGame() {

    }

    public BoardGame(String title, String description, int rating, int minPlayers, int maxPlayers, int playTime) {
        super(title, description, rating, minPlayers, maxPlayers);
        this.playTime = playTime;
    }

    // region Getter and Setter

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    // endregion
}
