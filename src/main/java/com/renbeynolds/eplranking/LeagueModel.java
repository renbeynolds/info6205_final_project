package com.renbeynolds.eplranking;

public class LeagueModel {

    private int numGames = 0;
    private double totalHomeScored = 0;
    private double totalHomeConceded = 0;
    private double totalAwayScored = 0;
    private double totalAwayConceded = 0;

    public void addMatch(MatchInfo match) {
        this.numGames += 1;
        this.totalHomeScored += match.getHomeGoals();
        this.totalHomeConceded = match.getAwayGoals();
        this.totalAwayScored += match.getAwayGoals();
        this.totalAwayConceded += match.getHomeGoals();
    }

    public double getAvgHomeScored() {
        return totalHomeScored / numGames;
    }

    public double getAvgHomeConceded() {
        return totalHomeConceded / numGames;
    }

    public double getAvgAwayScored() {
        return totalAwayScored / numGames;
    }

    public double getAvgAwayConceded() {
        return totalAwayConceded / numGames;
    }

}