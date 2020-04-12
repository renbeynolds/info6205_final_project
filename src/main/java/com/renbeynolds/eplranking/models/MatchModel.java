package com.renbeynolds.eplranking.models;

import lombok.Getter;

@Getter
public class MatchModel {

    private String homeTeamName;
    private String awayTeamName;
    private int homeGoals;
    private int awayGoals;

    public MatchModel(String homeTeamName, String awayTeamName, int homeGoals, int awayGoals) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

}