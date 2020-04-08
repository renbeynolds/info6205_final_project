package com.renbeynolds.eplranking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamModelBuilder {

    private Map<String,TeamModel> teams;
    private Set<MatchInfo> matches;

    public TeamModelBuilder(Set<MatchInfo> matches) {
        this.teams = new HashMap<String,TeamModel>();
        this.matches = matches;
        matches.forEach((MatchInfo match) -> {
            this.teams.putIfAbsent(match.getHomeTeam(), new TeamModel());
            this.teams.putIfAbsent(match.getAwayTeam(), new TeamModel());
        });
    }

    public Map<String,TeamModel> build() {
        for(MatchInfo match : matches) {
            teams.get(match.getHomeTeam()).addHomeMatch(match);
            teams.get(match.getAwayTeam()).addAwayMatch(match);
        }
        return teams;
    }
}