package com.renbeynolds.eplranking.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.renbeynolds.eplranking.models.TeamModel;

import picocli.CommandLine.Command;

@Command(name = "rank", description = "display a table ranking all teams")
public class Rank extends BaseCommand {

    @Override
    public void run() {
        super.run();
        simulator.rank();
        printRankingTable();
    }

    private void printRankingTable() {
        System.out.println("Rank  Team Name          Expected Points");
        List<TeamModel> teamList = new ArrayList<TeamModel>(simulator.getTeamModels().values());
        Collections.sort(teamList, Collections.reverseOrder());
        for (int i = 0; i < teamList.size(); i ++) {
            System.out.println(String.format("%-4d  %-17s  %.2f", i+1, teamList.get(i).getName(), teamList.get(i).getPoints()));
        }
    }

}