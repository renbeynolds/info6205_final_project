package com.renbeynolds.eplranking;

public class TeamModel {

    private int numHomeGames = 0;
    private int numAwayGames = 0;
    private double totalHomeScored = 0;
    private double totalHomeConceded = 0;
    private double totalAwayScored = 0;
    private double totalAwayConceded = 0;

    public void addHomeMatch(MatchInfo match) {
        this.numHomeGames += 1;
        this.totalHomeScored += match.getHomeGoals();
        this.totalHomeConceded += match.getAwayGoals();
    }

    public void addAwayMatch(MatchInfo match) {
        this.numAwayGames += 1;
        this.totalAwayScored += match.getAwayGoals();
        this.totalAwayConceded += match.getHomeGoals();
    }

    public double getAvgHomeScored() {
        return totalHomeScored / numHomeGames;
    }

    public double getAvgHomeConceded() {
        return totalHomeConceded / numHomeGames;
    }

    public double getAvgAwayScored() {
        return totalAwayScored / numAwayGames;
    }

    public double getAvgAwayConceded() {
        return totalAwayConceded / numAwayGames;
    }

}