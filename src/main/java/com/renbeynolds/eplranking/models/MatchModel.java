package com.renbeynolds.eplranking.models;

import lombok.Getter;

@Getter
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

}