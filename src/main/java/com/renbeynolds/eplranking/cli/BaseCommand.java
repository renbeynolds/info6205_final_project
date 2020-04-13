package com.renbeynolds.eplranking.cli;

import java.util.Set;

import com.renbeynolds.eplranking.DataLoader;
import com.renbeynolds.eplranking.MatchData;
import com.renbeynolds.eplranking.Simulator;

import picocli.CommandLine.Option;

public class BaseCommand implements Runnable {

    @Option(names = {"-d", "--data"}, required = true, description = "path to directory of data CSVs")
    String dataDir;

    @Option(names = {"-f", "--firstSeasonStartYear"}, required = true, description = "starting year of first season to consider")
    int firstSeasonStartYear;

    @Option(names = {"-l", "--lastSeasonStartYear"}, required = true, description = "starting year of last season to consider")
    int lastSeasonStartYear;

    protected Simulator simulator;

    public void run() {
        Set<MatchData> MatchDatas = DataLoader.loadHistoricData(
            dataDir,
            firstSeasonStartYear,
            lastSeasonStartYear
        );
        this.simulator = new Simulator(MatchDatas);
    }
    
}