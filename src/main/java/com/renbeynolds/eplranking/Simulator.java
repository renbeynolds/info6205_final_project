package com.renbeynolds.eplranking;

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

    public double[] simulateMatch(String homeTeamName, String awayTeamName) {
        double muHome = teamModels.get(homeTeamName).getHomeAttackStrength() *
                teamModels.get(awayTeamName).getAwayDefenseStrength() * leagueModel.getAvgHomeScored();
        double muAway = teamModels.get(awayTeamName).getAwayAttackStrength() *
                teamModels.get(homeTeamName).getHomeDefenseStrength() * leagueModel.getAvgAwayScored();

        double probabilityHomeWin = 0;
        double probabilityTie = 0;
        double probabilityAwayWin = 0;

        // set upper bound on goals scored by each team to 10
        for(int homeGoals = 0; homeGoals < 11; homeGoals++) {
            for(int awayGoals = 0; awayGoals < 11; awayGoals++) {
                double p = Poisson.pmf(homeGoals, muHome) * Poisson.pmf(awayGoals, muAway);
                if(homeGoals == awayGoals) {
                    probabilityTie += p;
                } else if(homeGoals > awayGoals) {
                    probabilityHomeWin += p;
                } else {
                    probabilityAwayWin += p;
                }
            }
        }

        double homePoints = 3 * probabilityHomeWin + probabilityTie;
        double awayPoints = 3 * probabilityAwayWin + probabilityTie;
        return new double[]{homePoints, awayPoints};
    }

}