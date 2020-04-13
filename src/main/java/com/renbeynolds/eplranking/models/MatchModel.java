package com.renbeynolds.eplranking.models;

import com.renbeynolds.eplranking.MatchData;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchModel {

    private double pHome;
    private double pTie;
    private double pAway;

    private double highestProbability;
    private int mostLikelyHomeGoals;
    private int mostLikelyAwayGoals;

    @Getter(value = AccessLevel.NONE)
    private double[][] probabilities = new double[11][11];

    public void addScore(int homeGoals, int awayGoals, double p) {
        this.probabilities[homeGoals][awayGoals] = p;

        if(p > highestProbability) {
            this.highestProbability = p;
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

    public double getHomePoints() {
        return 3 * pHome + pTie;
    }

    public double getAwayPoints() {
        return 3 * pAway + pTie;
    }

    public double getProbability(int homeGoals, int awayGoals) {
        return probabilities[homeGoals][awayGoals];
    }

    public MatchModel(MatchData matchData) {
        this.pHome = matchData.getHomeGoals() > matchData.getAwayGoals() ? 1.0 : 0.0;
        this.pTie = matchData.getHomeGoals() == matchData.getAwayGoals() ? 1.0 : 0.0;
        this.pAway = matchData.getHomeGoals() < matchData.getAwayGoals() ? 1.0 : 0.0;
        this.highestProbability = 1.0;
        this.mostLikelyHomeGoals = matchData.getHomeGoals();
        this.mostLikelyAwayGoals = matchData.getAwayGoals();
    }

}