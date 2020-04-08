package com.renbeynolds.eplranking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        for(MatchInfo match : matches) {
            league.addMatch(match);
            teams.get(match.getHomeTeam()).addHomeMatch(match);
            teams.get(match.getAwayTeam()).addAwayMatch(match);
        }
    }

    public Map<String,TeamModel> getTeamModels() {
        return teams;
    }

    public LeagueModel getLeagueModel() {
        return league;        
    }
}