package com.renbeynolds.eplranking.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.renbeynolds.eplranking.MatchInfo;

public class ModelBuilder {

    private LeagueModel league;
    private Map<String,TeamModel> teams;
    private Set<MatchInfo> matches;

    public ModelBuilder(Set<MatchInfo> matches) {
        this.league = new LeagueModel();
        this.teams = new HashMap<String,TeamModel>();
        this.matches = matches;
        matches.forEach((MatchInfo match) -> {
            this.teams.putIfAbsent(match.getHomeTeam(), new TeamModel());
            this.teams.putIfAbsent(match.getAwayTeam(), new TeamModel());
        });
    }

    public void build() {
        // Perform inital counts for home and away scores
        for(MatchInfo match : matches) {
            league.addMatch(match);
            teams.get(match.getHomeTeam()).addHomeMatch(match);
            teams.get(match.getAwayTeam()).addAwayMatch(match);
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