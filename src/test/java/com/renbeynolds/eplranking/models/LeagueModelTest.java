package com.renbeynolds.eplranking.models;

import static org.junit.Assert.assertEquals;

import com.renbeynolds.eplranking.MatchData;

import org.junit.Test;

public class LeagueModelTest {

    @Test
    public void canComputeAverages() {
        LeagueModel model = new LeagueModel();

        model.addMatch(new MatchData("home", "away", 2, 1));
        model.addMatch(new MatchData("home", "away", 4, 2));
        model.addMatch(new MatchData("home", "away", 6, 3));

        assertEquals(4, model.getAvgHomeScored(), .001);
        assertEquals(2, model.getAvgAwayScored(), .001);
        assertEquals(2, model.getAvgHomeConceded(), .001);
        assertEquals(4, model.getAvgAwayConceded(), .001);
    }

}