package com.renbeynolds.eplranking.models;

import lombok.Getter;

@Getter
public class MatchModel {

    private final double pHome;
    private final double pTie;
    private final double pAway;
    private final double homePoints;
    private final double awayPoints;

    private final double highestProbability;
    private final int mostLikelyHomeGoals;
    private final int mostLikelyAwayGoals;

    public MatchModel(double pHome, double pTie, double pAway, double highestProbability, int mostLikelyHomeGoals, int mostLikelyAwayGoals) {
        this.pHome = pHome;
        this.pTie = pTie;
        this.pAway = pAway;
        this.homePoints = 3 * pHome + pTie;
        this.awayPoints = 3 * pAway + pTie;
        this.highestProbability = highestProbability;
        this.mostLikelyHomeGoals = mostLikelyHomeGoals;
        this.mostLikelyAwayGoals = mostLikelyAwayGoals;
    }

}