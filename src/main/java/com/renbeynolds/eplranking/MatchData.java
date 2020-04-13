package com.renbeynolds.eplranking;

import lombok.Getter;

@Getter
public class MatchData {

    private String homeTeamName;
    private String awayTeamName;
    private int homeGoals;
    private int awayGoals;

    public MatchData(String homeTeamName, String awayTeamName, int homeGoals, int awayGoals) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

}