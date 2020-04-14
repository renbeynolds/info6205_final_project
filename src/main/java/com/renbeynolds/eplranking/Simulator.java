package com.renbeynolds.eplranking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.renbeynolds.eplranking.models.LeagueModel;
import com.renbeynolds.eplranking.models.MatchModel;
import com.renbeynolds.eplranking.models.TeamModel;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Simulator {

    @Setter
    private Map<String, Map<String, MatchData>> partialSeasonMatchData;
    private Set<MatchData> historicMatchData;
    private Map<String,TeamModel> teamModels;
    private LeagueModel leagueModel;

    public Simulator(Set<MatchData> historicMatchData) {
        this.historicMatchData = historicMatchData;
    }

    public Map<String, Map<String, MatchModel>> simulateSeason() {
        Map<String, Map<String, MatchModel>> matchModels = new HashMap<String, Map<String,MatchModel>>(); 
        // each team plays each other team twice, once at home and once away
        for (Map.Entry<String,TeamModel> homeTeam : teamModels.entrySet()) {
            Map<String,MatchModel> teamResults = new HashMap<String, MatchModel>();
            for (Map.Entry<String,TeamModel> awayTeam : teamModels.entrySet()) {
                if(!homeTeam.equals(awayTeam)) {
                    MatchModel result= simulateMatch(homeTeam.getKey(), awayTeam.getKey());
                    homeTeam.getValue().addPoints(result.getHomePoints());
                    awayTeam.getValue().addPoints(result.getAwayPoints());
                    teamResults.put(awayTeam.getKey(), result);
                }
            }
            matchModels.put(homeTeam.getKey(), teamResults);
        }
        return matchModels;
    }

    public MatchModel simulateMatch(String homeTeamName, String awayTeamName) {
        
        if(haveRealMatchData(partialSeasonMatchData, homeTeamName, awayTeamName)) {
            return new MatchModel(partialSeasonMatchData.get(homeTeamName).get(awayTeamName));
        }

        double muHome = teamModels.get(homeTeamName).getHomeAttackStrength() *
                teamModels.get(awayTeamName).getAwayDefenseStrength() * leagueModel.getAvgHomeScored();
        double muAway = teamModels.get(awayTeamName).getAwayAttackStrength() *
                teamModels.get(homeTeamName).getHomeDefenseStrength() * leagueModel.getAvgAwayScored();

        MatchModel matchModel = new MatchModel();

        // set upper bound on goals scored by each team to 10
        for(int homeGoals = 0; homeGoals < 11; homeGoals++) {
            for(int awayGoals = 0; awayGoals < 11; awayGoals++) {
                double p = Poisson.pmf(homeGoals, muHome) * Poisson.pmf(awayGoals, muAway);
                matchModel.addScore(homeGoals, awayGoals, p);
            }
        }

        return matchModel;
    }

    public void buildModels() {
        this.leagueModel = new LeagueModel();
        this.teamModels = new TreeMap<String,TeamModel>();

        // perform initial counts for home and away scores
        this.historicMatchData.forEach((MatchData match) -> {
            leagueModel.addMatch(match);

            if(this.partialSeasonMatchData == null || this.partialSeasonMatchData.containsKey(match.getHomeTeamName())) {
                this.teamModels.putIfAbsent(match.getHomeTeamName(), new TeamModel(match.getHomeTeamName()));
                teamModels.get(match.getHomeTeamName()).addHomeMatch(match);
            }

            if(this.partialSeasonMatchData == null || this.partialSeasonMatchData.containsKey(match.getAwayTeamName())) {
                this.teamModels.putIfAbsent(match.getAwayTeamName(), new TeamModel(match.getAwayTeamName()));
                teamModels.get(match.getAwayTeamName()).addAwayMatch(match);
            }
        });

        // normalize team models based on overall league data
        for (Map.Entry<String,TeamModel> team : teamModels.entrySet()) {
            team.getValue().normalize(leagueModel);
        }  
    }

    protected boolean haveRealMatchData(Map<String, Map<String, MatchData>> map, String key1, String key2) {
        return map != null && map.containsKey(key1) && map.get(key1).containsKey(key2);
    }

}