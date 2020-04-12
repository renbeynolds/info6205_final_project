package com.renbeynolds.eplranking.models;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import lombok.Getter;

@Getter
public class ModelBuilder {

    private LeagueModel leagueModel;
    private Map<String,TeamModel> teamModels;
    private Set<MatchModel> matchModels;

    public ModelBuilder(Set<MatchModel> matchModels) {
        this.leagueModel = new LeagueModel();
        this.teamModels = new TreeMap<String,TeamModel>();
        this.matchModels = matchModels;
        matchModels.forEach((MatchModel match) -> {
            this.teamModels.putIfAbsent(match.getHomeTeamName(), new TeamModel(match.getHomeTeamName()));
            this.teamModels.putIfAbsent(match.getAwayTeamName(), new TeamModel(match.getAwayTeamName()));
        });
    }

    public void build() {
        // Perform inital counts for home and away scores
        for(MatchModel match : matchModels) {
            leagueModel.addMatch(match);
            teamModels.get(match.getHomeTeamName()).addHomeMatch(match);
            teamModels.get(match.getAwayTeamName()).addAwayMatch(match);
        }

        // normalize team models based on overall league data
        for (Map.Entry<String,TeamModel> team : teamModels.entrySet()) {
            team.getValue().normalize(leagueModel);
        }  
    }

}