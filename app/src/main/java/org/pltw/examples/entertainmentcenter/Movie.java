package org.pltw.examples.entertainmentcenter;

public class Movie extends Entertainment {
    private int duration;
    // Todo: Turn into class or enum
    private String mPAARating;

    public Movie() {
    }

    public Movie(String title, String description, int rating, int duration, String mPAARating) {
        super.setTitle(title);
        super.setDescription(description);
        super.setRating(rating);
        this.duration = duration;
        this.mPAARating = mPAARating;
    }

    // region Getters and Setters

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getmPAARating() {
        return mPAARating;
    }

    public void setmPAARating(String PAARating) {
        mPAARating = PAARating;
    }

    // endregion
}
