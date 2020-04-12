package com.renbeynolds.eplranking.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModelBuilder {

    private LeagueModel league;
    private Map<String,TeamModel> teams;
    private Set<MatchModel> matches;

    public ModelBuilder(Set<MatchModel> matches) {
        this.league = new LeagueModel();
        this.teams = new HashMap<String,TeamModel>();
        this.matches = matches;
        matches.forEach((MatchModel match) -> {
            this.teams.putIfAbsent(match.getHomeTeamName(), new TeamModel());
            this.teams.putIfAbsent(match.getAwayTeamName(), new TeamModel());
        });
    }

    public void build() {
        // Perform inital counts for home and away scores
        for(MatchModel match : matches) {
            league.addMatch(match);
            teams.get(match.getHomeTeamName()).addHomeMatch(match);
            teams.get(match.getAwayTeamName()).addAwayMatch(match);
        }

        // normalize team models based on overall league data
        for (Map.Entry<String,TeamModel> team : teams.entrySet()) {
            team.getValue().normalize(league);
        }  
    }

    public Map<String,TeamModel> getTeamModels() {
        return teams;
    }

    public LeagueModel getLeagueModel() {
        return league;        
    }
}