package com.renbeynolds.eplranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import com.renbeynolds.eplranking.models.MatchModel;

public class DataLoader {

    public static Set<MatchModel> loadData(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
        Set<MatchModel> matches = new HashSet<MatchModel>();
        for(int year = firstSeasonStartYear; year < lastSeasonStartYear + 1; year++) {
            matches.addAll(readFile(Paths.get(dataDir, String.format("%d-%d.csv", year, year + 1)).toString()));
        }
        return matches;
    }

    private static Set<MatchModel> readFile(String filename) {
        String line = "";
        String cvsSplitBy = ",";
        Set<MatchModel> result = new HashSet<MatchModel>();

        int homeTeamNameColumn = 0;
        int awayTeamNameColumn = 0;
        int homeGoalsColumn = 0;
        int awayGoalsColumn = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if(row > 0) {
                    result.add(new MatchModel(
                        data[homeTeamNameColumn],
                        data[awayTeamNameColumn],
                        Integer.parseInt(data[homeGoalsColumn]),
                        Integer.parseInt(data[awayGoalsColumn])
                    ));
                } else {
                    homeTeamNameColumn = find(data, "HomeTeam");
                    awayTeamNameColumn = find(data, "AwayTeam");
                    homeGoalsColumn = find(data, "FTHG");
                    awayGoalsColumn = find(data, "FTAG");
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int find(String[] a, String target) {
        return IntStream.range(0, a.length)
                        .filter(i -> target.equals(a[i]))
                        .findFirst()
                        .orElse(-1);
    }

}