package com.renbeynolds.eplranking.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.renbeynolds.eplranking.models.TeamModel;

import picocli.CommandLine.Command;

@Command(name = "rank", description = "show table ranking all teams")
public class Rank extends BaseCommand {

    @Override
    public void run() {
        super.run();
        simulator.simulateSeason();
        printRankings();
    }

    private void printRankings() {
        System.out.println("Rank,Team Name,Expected Points");
        List<TeamModel> teamList = new ArrayList<TeamModel>(simulator.getTeamModels().values());

        Comparator<TeamModel> compareByPointsReverse = (TeamModel t1, TeamModel t2) -> {
            if(t1.getPoints() > t2.getPoints()) {
                return -1;
            } else if (t1.getPoints() == t2.getPoints()) {
                return 0;
            } else {
                return 1;
            }
        };

        Collections.sort(teamList, compareByPointsReverse);

        for (int i = 0; i < teamList.size(); i ++) {
            System.out.println(String.format("%d,%s,%.2f", i+1, teamList.get(i).getName(), teamList.get(i).getPoints()));
        }
    }

}