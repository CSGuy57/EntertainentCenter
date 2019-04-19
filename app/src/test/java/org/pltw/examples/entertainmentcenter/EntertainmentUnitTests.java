package org.pltw.examples.entertainmentcenter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EntertainmentUnitTests {
    @Test
    public void createMovieObject() {
        Movie m = new Movie("Almost Famous",
                        "A good movie",
                        10,
                        120,
                        "R");
        assertEquals("Almost Famous",m.getTitle());
        assertEquals("A good movie",m.getDescription());
        assertEquals(10, m.getRating());
        assertEquals(120, m.getDuration());
        assertEquals("R",m.getmPAARating());
    }
}