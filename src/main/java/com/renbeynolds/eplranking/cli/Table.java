package com.renbeynolds.eplranking.cli;

import java.util.Map;
import java.util.stream.Collectors;

import com.renbeynolds.eplranking.models.MatchModel;
import com.renbeynolds.eplranking.models.TeamModel;

import picocli.CommandLine.Command;

@Command(name = "table", description = "produce a table showing expected outcome of all matches")
public class Table extends BaseCommand {

    @Override
    public void run() {
        super.run();
        Map<String, Map<String, MatchModel>> MatchModels = simulator.simulateSeason();
        printTable(MatchModels);
    }

    private void printTable(Map<String, Map<String, MatchModel>> MatchModels) {
        Map<String,TeamModel> teamModels = simulator.getTeamModels();
        System.out.println("home/away,"+ teamModels.keySet().stream().map(s -> String.format("%s", s)).collect(Collectors.joining(",")));
        for(String homeTeamName : teamModels.keySet()) {
            System.out.printf(homeTeamName);
            for(String awayTeamName : teamModels.keySet()) {
                System.out.printf(",");
                if(!homeTeamName.equals(awayTeamName)) {
                    MatchModel result = MatchModels.get(homeTeamName).get(awayTeamName);
                    System.out.printf(String.format("%d - %d", result.getMostLikelyHomeGoals(), result.getMostLikelyAwayGoals()));
                } else {
                    System.out.printf("-");
                }
            }
            System.out.printf("\n");
        }
    }

}