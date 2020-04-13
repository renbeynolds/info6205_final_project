package com.renbeynolds.eplranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.renbeynolds.eplranking.models.MatchModel;

public class DataLoader {

    public static Set<MatchModel> loadHistoricData(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
        Set<MatchModel> matches = new HashSet<MatchModel>();
        for(int year = firstSeasonStartYear; year < lastSeasonStartYear + 1; year++) {
            matches.addAll(readFile(Paths.get(dataDir, String.format("%d-%d.csv", year, year + 1)).toString()));
        }
        return matches;
    }

    public static Map<String, Map<String, MatchModel>> loadPartialSeason(String filepath) {
        Set<MatchModel> models = readFile(filepath);
        Map<String, Map<String, MatchModel>> organizedModels = new HashMap<String, Map<String,MatchModel>>();
        models.forEach((MatchModel match) -> {
            String home = match.getHomeTeamName();
            String away = match.getAwayTeamName();
            if(!organizedModels.keySet().contains(home)) {
                organizedModels.put(home, new HashMap<String,MatchModel>());
            }
            organizedModels.get(home).put(away, match);
        });
        return organizedModels;
    }

    private static Set<MatchModel> readFile(String filepath) {
        String line = "";
        String cvsSplitBy = ",";
        Set<MatchModel> result = new HashSet<MatchModel>();

        int homeTeamNameColumn = 0;
        int awayTeamNameColumn = 0;
        int homeGoalsColumn = 0;
        int awayGoalsColumn = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
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