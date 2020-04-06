package com.renbeynolds.eplranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<MatchInfo> read(String filename) {
        String line = "";
        String cvsSplitBy = ",";
        List<MatchInfo> result = new ArrayList<MatchInfo>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int row = 0;
            while ((line = br.readLine()) != null) {
                if(row > 0) {
                    String[] data = line.split(cvsSplitBy);
                    result.add(new MatchInfo(data));
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}