package com.renbeynolds.eplranking.models;

public class MatchModel {

    private String homeTeamName;
    private String awayTeamName;
    private int homeGoals;
    private int awayGoals;

    public MatchModel(String[] data) {
        homeTeamName = data[2];
        awayTeamName = data[3];
        homeGoals = Integer.parseInt(data[4]);
        awayGoals = Integer.parseInt(data[5]);
    }

    public String getHomeTeamName() { return this.homeTeamName; }
    public String getAwayTeamName() { return this.awayTeamName; }
    public int getHomeGoals() { return this.homeGoals; }
    public int getAwayGoals() { return this.awayGoals; }

}