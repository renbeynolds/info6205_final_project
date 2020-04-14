package com.renbeynolds.eplranking.models;

import static org.junit.Assert.assertEquals;

import com.renbeynolds.eplranking.MatchData;

import org.junit.Test;

public class MatchModelTest {

    @Test
    public void canConstructFromMatchData() {
        MatchData data = new MatchData("home", "away", 3, 1);
        MatchModel model = new MatchModel(data);
        assertEquals(1, model.getPHome(), .001);
        assertEquals(0, model.getPTie(), .001);
        assertEquals(0, model.getPAway(), .001);
        assertEquals(1, model.getHighestProbability(), .001);
        assertEquals(3, model.getHomePoints(), .001);
        assertEquals(0, model.getAwayPoints(), .001);
    }

    @Test
    public void canConstructFromMatchSimulation() {
        MatchModel model = new MatchModel();
        model.addScore(0, 0, .1);
        model.addScore(1, 0, .1);
        model.addScore(2, 0, .1);
        model.addScore(0, 1, .1);
        model.addScore(0, 2, .1);
        model.addScore(1, 1, .3);
        model.addScore(2, 2, .2);
        assertEquals(.3, model.getHighestProbability(), .001);
        assertEquals(1, model.getMostLikelyHomeGoals());
        assertEquals(1, model.getMostLikelyAwayGoals());
        assertEquals(.6, model.getPTie(), .001);

    }

}