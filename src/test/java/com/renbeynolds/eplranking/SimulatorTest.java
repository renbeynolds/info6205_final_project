package com.renbeynolds.eplranking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SimulatorTest {

    @Test
    public void canCheckHaveRealMatchData() {
        Simulator simulator = new Simulator(new HashSet<MatchData>());

        assertFalse(simulator.haveRealMatchData(null, "home", "away"));

        Map<String, Map<String, MatchData>> data = new HashMap<String, Map<String,MatchData>>();
        assertFalse(simulator.haveRealMatchData(data, "home", "away"));

        Map<String, MatchData> homeMatches = new HashMap<String,MatchData>();
        data.put("home", homeMatches);
        assertFalse(simulator.haveRealMatchData(data, "home", "away"));

        homeMatches.put("away", new MatchData("home", "away", 0, 0));
        assertTrue(simulator.haveRealMatchData(data, "home", "away"));
    }

    @Test
    public void canBuildModelsWithoutPartialSeason() {
        Set<MatchData> historicMatchData = new HashSet<MatchData>();
        historicMatchData.add(new MatchData("team1", "team2", 1, 2));
        historicMatchData.add(new MatchData("team2", "team1", 3, 0));
        
        Simulator simulator = new Simulator(historicMatchData);
        assertNull(simulator.getLeagueModel());
        assertNull(simulator.getTeamModels());
        
        simulator.buildModels();
        assertEquals(2, simulator.getTeamModels().size());
        assertEquals(2, simulator.getLeagueModel().getAvgHomeScored(), .001);
        assertEquals(1, simulator.getLeagueModel().getAvgAwayScored(), .001);
        assertEquals(.5, simulator.getTeamModels().get("team1").getHomeAttackStrength(), .001);
    }

    @Test
    public void canBuildModelsWithPartialSeason() {
        Set<MatchData> historicMatchData = new HashSet<MatchData>();
        historicMatchData.add(new MatchData("team1", "team2", 1, 3));
        historicMatchData.add(new MatchData("team2", "team1", 2, 1));
        historicMatchData.add(new MatchData("team3", "team1", 3, 2));

        Map<String, Map<String, MatchData>> partialSeasonData = new HashMap<String, Map<String,MatchData>>();
        Map<String, MatchData> team1Matches = new HashMap<String,MatchData>();
        Map<String, MatchData> team2Matches = new HashMap<String,MatchData>();
        partialSeasonData.put("team1", team1Matches);
        partialSeasonData.put("team2", team2Matches);

        team1Matches.put("team2", new MatchData("team1", "team2", 0, 0));
        team2Matches.put("team1", new MatchData("team1", "team2", 0, 0));
        
        Simulator simulator = new Simulator(historicMatchData);  
        simulator.setPartialSeasonMatchData(partialSeasonData);      
        simulator.buildModels();

        assertEquals(2, simulator.getTeamModels().size());
        assertEquals(2, simulator.getLeagueModel().getAvgHomeScored(), .001);
        assertEquals(2, simulator.getLeagueModel().getAvgAwayScored(), .001);
    }

}