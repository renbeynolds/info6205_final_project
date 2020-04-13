package com.renbeynolds.eplranking.models;

import com.renbeynolds.eplranking.MatchData;

import lombok.Getter;

@Getter
public class TeamModel implements Comparable<TeamModel> {

    public TeamModel(String name) {
        this.name = name;
    }

    private final String name;

    private int numHomeGames = 0;
    private int numAwayGames = 0;
    private double totalHomeScored = 0;
    private double totalHomeConceded = 0;
    private double totalAwayScored = 0;
    private double totalAwayConceded = 0;

    private double homeAttackStrength;
    private double homeDefenseStrength;
    private double awayAttackStrength;
    private double awayDefenseStrength;

    private double points = 0;

    public void addHomeMatch(MatchData match) {
        this.numHomeGames += 1;
        this.totalHomeScored += match.getHomeGoals();
        this.totalHomeConceded += match.getAwayGoals();
    }

    public void addAwayMatch(MatchData match) {
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
    
    public double getHomeAttackStrength() {
        return homeAttackStrength;
    }

    public double getHomeDefenseStrength() {
        return homeDefenseStrength;
    }

    public double getAwayAttackStrength() {
        return awayAttackStrength;
    }

    public double getAwayDefenseStrength() {
        return awayDefenseStrength;
    }

    public void normalize(LeagueModel league) {
        this.homeAttackStrength = getAvgHomeScored() / league.getAvgHomeScored();
        this.homeDefenseStrength = getAvgHomeConceded() / league.getAvgHomeConceded();
        this.awayAttackStrength = getAvgAwayScored() / league.getAvgAwayScored();
        this.awayDefenseStrength = getAvgAwayConceded() / league.getAvgAwayConceded();
    }

    public void addPoints(double additionalPoints) {
        this.points += additionalPoints;
    }

    // used to rank teams by points
    public int compareTo(TeamModel other) { 
        return this.name.compareTo(other.name);
    } 

}