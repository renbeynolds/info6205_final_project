package com.renbeynolds.eplranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.renbeynolds.eplranking.models.LeagueModel;
import com.renbeynolds.eplranking.models.MatchModel;
import com.renbeynolds.eplranking.models.ModelBuilder;
import com.renbeynolds.eplranking.models.TeamModel;

public class Simulator {

    private final Set<MatchModel> matchModels;
    private final Map<String,TeamModel> teamModels;
    private final LeagueModel leagueModel;

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

    public void getRankings() {
        // each team plays each other team twice, once at home and once away
        for (Map.Entry<String,TeamModel> homeTeam : teamModels.entrySet()) {
            for (Map.Entry<String,TeamModel> awayTeam : teamModels.entrySet()) {
                if(!homeTeam.equals(awayTeam)) {
                    MatchResult result = simulateMatch(homeTeam.getKey(), awayTeam.getKey());
                    homeTeam.getValue().addPoints(result.homePoints);
                    awayTeam.getValue().addPoints(result.awayPoints);
                }
            }
        }
        printRankingTable();
    }

    private void printRankingTable() {
        List<TeamModel> teamList = new ArrayList<TeamModel>(teamModels.values());
        Collections.sort(teamList, Collections.reverseOrder());
        for (int i = 0; i < teamList.size(); i ++) {
            System.out.println(String.format("%d - %s - %.2f", i+1, teamList.get(i).getName(), teamList.get(i).getPoints()));
        }
    }

    private MatchResult simulateMatch(String homeTeamName, String awayTeamName) {
        double muHome = teamModels.get(homeTeamName).getHomeAttackStrength() *
                teamModels.get(awayTeamName).getAwayDefenseStrength() * leagueModel.getAvgHomeScored();
        double muAway = teamModels.get(awayTeamName).getAwayAttackStrength() *
                teamModels.get(homeTeamName).getHomeDefenseStrength() * leagueModel.getAvgAwayScored();

        double pHome = 0;
        double pTie = 0;
        double pAway = 0;

        // set upper bound on goals scored by each team to 10
        for(int homeGoals = 0; homeGoals < 11; homeGoals++) {
            for(int awayGoals = 0; awayGoals < 11; awayGoals++) {
                double p = Poisson.pmf(homeGoals, muHome) * Poisson.pmf(awayGoals, muAway);
                if(homeGoals == awayGoals) {
                    pTie += p;
                } else if(homeGoals > awayGoals) {
                    pHome += p;
                } else {
                    pAway += p;
                }
            }
        }

        return new MatchResult(pHome, pTie, pAway);
    }

    private class MatchResult {
        final double pHome;
        final double pTie;
        final double pAway;
        final double homePoints;
        final double awayPoints;

        MatchResult(double pHome, double pTie, double pAway) {
            this.pHome = pHome;
            this.pTie = pTie;
            this.pAway = pAway;
            this.homePoints = 3 * pHome + pTie;
            this.awayPoints = 3 * pAway + pTie;
        }
    }

}