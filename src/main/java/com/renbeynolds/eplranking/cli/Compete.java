package com.renbeynolds.eplranking.cli;

import com.renbeynolds.eplranking.models.MatchModel;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "compete", description = "show expected outcome of competition between two teams")
public class Compete extends BaseCommand {

    @Option(names = {"-h", "--homeTeamName"}, required = true, description = "name of home team")
    String homeTeamName;

    @Option(names = {"-a", "--awayTeamName"}, required = true, description = "name of away team")
    String awayTeamName;

    @Option(names = {"-s", "--summary"}, description = "print summary instead of CSV table of all outcomes")
    boolean summary;

    @Override
    public void run() {
        super.run();
        simulator.buildModels();
        MatchModel result = simulator.simulateMatch(homeTeamName, awayTeamName);
        if(summary) {
            printSummary(result);
        } else {
            printProbabilityTable(result);
        }
    }

    private void printProbabilityTable(MatchModel result) {
        System.out.println(String.format("%s Goals, %s Goals, Probability", homeTeamName, awayTeamName));
        for(int homeGoals = 0; homeGoals < 11; homeGoals++) {
            for(int awayGoals = 0; awayGoals < 11; awayGoals++) {
                System.out.println(String.format("%d, %d, %.4f", homeGoals, awayGoals, result.getProbability(homeGoals, awayGoals)));
            }
        }
    }

    private void printSummary(MatchModel result) {
        System.out.println(String.format("Home: %-15s Away: %s\n", homeTeamName, awayTeamName));
        System.out.println(String.format("Probability of home win: %.4f", result.getPHome()));
        System.out.println(String.format("Probability of tie     : %.4f", result.getPTie()));
        System.out.println(String.format("Probability of away win: %.4f\n", result.getPAway()));
        System.out.println("Most likely outcome:");
        if(result.getMostLikelyHomeGoals() > result.getMostLikelyAwayGoals()) {
            System.out.println(String.format("%s beats %s with a score of %d - %d (probability %.4f)",
                    homeTeamName, awayTeamName, result.getMostLikelyHomeGoals(), result.getMostLikelyAwayGoals(), result.getHighestProbability()));
        }else if(result.getMostLikelyHomeGoals() < result.getMostLikelyAwayGoals()) {
            System.out.println(String.format("%s beats %s with a score of %d - %d (probability %.4f)",
                    awayTeamName, homeTeamName, result.getMostLikelyAwayGoals(), result.getMostLikelyHomeGoals(), result.getHighestProbability()));
        } else {
            System.out.println(String.format("%s ties %s with a score of %d - %d (probability %.4f)",
                    homeTeamName, awayTeamName, result.getMostLikelyHomeGoals(), result.getMostLikelyAwayGoals(), result.getHighestProbability()));
        }
    }

}