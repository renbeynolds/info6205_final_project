package com.renbeynolds.eplranking;

public class MatchInfo {

    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;

    public MatchInfo(String[] data) {
        homeTeam = data[2];
        awayTeam = data[3];
        homeGoals = Integer.parseInt(data[4]);
        awayGoals = Integer.parseInt(data[5]);
    }

    public String getHomeTeam() { return this.homeTeam; }
    public String getAwayTeam() { return this.awayTeam; }
    public int getHomeGoals() { return this.homeGoals; }
    public int getAwayGoals() { return this.awayGoals; }

}