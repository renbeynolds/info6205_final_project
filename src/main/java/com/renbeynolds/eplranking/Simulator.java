package com.renbeynolds.eplranking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.renbeynolds.eplranking.models.LeagueModel;
import com.renbeynolds.eplranking.models.MatchModel;
import com.renbeynolds.eplranking.models.ModelBuilder;
import com.renbeynolds.eplranking.models.TeamModel;

import lombok.Getter;

@Getter
public class Simulator {

    private final Set<MatchModel> matchModels;
    private final Map<String,TeamModel> teamModels;
    private final LeagueModel leagueModel;
    private Map<String, Map<String, MatchResult>> matchResults = new HashMap<String, Map<String,MatchResult>>(); 

    public Simulator(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
        this.matchModels = DataLoader.loadData(
            dataDir,
            firstSeasonStartYear,
            lastSeasonStartYear
        );
        ModelBuilder builder = new ModelBuilder(this.matchModels);
        builder.build();
        this.teamModels = builder.getTeamModels();
        this.leagueModel = builder.getLeagueModel();
    }

    public void simulateSeason() {
        // each team plays each other team twice, once at home and once away
        for (Map.Entry<String,TeamModel> homeTeam : teamModels.entrySet()) {
            Map<String,MatchResult> teamResults = new HashMap<String, MatchResult>();
            for (Map.Entry<String,TeamModel> awayTeam : teamModels.entrySet()) {
                if(!homeTeam.equals(awayTeam)) {
                    MatchResult result = simulateMatch(homeTeam.getKey(), awayTeam.getKey());
                    homeTeam.getValue().addPoints(result.getHomePoints());
                    awayTeam.getValue().addPoints(result.getAwayPoints());
                    teamResults.put(awayTeam.getKey(), result);
                }
            }
            matchResults.put(homeTeam.getKey(), teamResults);
        }
    }

    public MatchResult simulateMatch(String homeTeamName, String awayTeamName) {
        double muHome = teamModels.get(homeTeamName).getHomeAttackStrength() *
                teamModels.get(awayTeamName).getAwayDefenseStrength() * leagueModel.getAvgHomeScored();
        double muAway = teamModels.get(awayTeamName).getAwayAttackStrength() *
                teamModels.get(homeTeamName).getHomeDefenseStrength() * leagueModel.getAvgAwayScored();

        double pHome = 0;
        double pTie = 0;
        double pAway = 0;

        double highestProbability = 0;
        int mostLikelyHomeGoals = 0;
        int mostLikelyAwayGoals = 0;

        // set upper bound on goals scored by each team to 10
        for(int homeGoals = 0; homeGoals < 11; homeGoals++) {
            for(int awayGoals = 0; awayGoals < 11; awayGoals++) {
                double p = Poisson.pmf(homeGoals, muHome) * Poisson.pmf(awayGoals, muAway);
                if(p > highestProbability) {
                    highestProbability = p;
                    mostLikelyHomeGoals = homeGoals;
                    mostLikelyAwayGoals = awayGoals;
                }
                if(homeGoals == awayGoals) {
                    pTie += p;
                } else if(homeGoals > awayGoals) {
                    pHome += p;
                } else {
                    pAway += p;
                }
            }
        }

        return new MatchResult(pHome, pTie, pAway, highestProbability, mostLikelyHomeGoals, mostLikelyAwayGoals);
    }

}