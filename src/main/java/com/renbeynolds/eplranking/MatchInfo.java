package com.renbeynolds.eplranking;

public class MatchInfo {

    private String homeTeam;
    private String awayTeam;
    private Integer homeGoals;
    private Integer awayGoals;

    public MatchInfo(String[] data) {
        homeTeam = data[2];
        awayTeam = data[3];
        homeGoals = Integer.valueOf(data[4]);
        awayGoals = Integer.valueOf(data[5]);
    }

    public String getHomeTeam() { return this.homeTeam; }
    public String getAwayTeam() { return this.awayTeam; }
    public Integer getHomeGoals() { return this.homeGoals; }
    public Integer getAwayGoals() { return this.awayGoals; }

}