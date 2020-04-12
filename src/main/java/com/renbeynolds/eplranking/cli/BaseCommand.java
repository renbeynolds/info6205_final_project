package com.renbeynolds.eplranking.cli;

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
        this.simulator = new Simulator(dataDir, firstSeasonStartYear, lastSeasonStartYear);
    }
    
}