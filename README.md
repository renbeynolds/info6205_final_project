# INFO 6205 Final Project

This application is a ranking system capable of predicting league standings for the English Premier League based on past match outcomes.

Data sets sourced from [football-data.co.uk](http://www.football-data.co.uk/englandm.php).

- [Running Tests](#running-tests)
- [Building the CLI](#building-the-cli)
- [Running the CLI](#running-the-cli)
    - [Command Line Help](#command-line-help)
    - [Team Rankings Based on Historic Data](#team-rankings-based-on-historic-data)
    - [Expected Outcome of Single Match](#expected-outcome-of-single-match)
    - [Season Result Table](#season-result-table)

## Running Tests
```
./gradlew test
```

## Building the CLI
```
./gradlew shadowjar
```

## Running the CLI

#### Command Line Help

List the available commands and options.

```bash
java -jar build/libs/eplranking.jar --help
```

#### Team Rankings Based on Historic Data

Produces a CSV table of team ranks based on the expected number of points achieved in a tournament of all teams present in the input data set.

```bash
java -jar build/libs/eplranking.jar \
    rank \
    -d ./datasets \
    -f 2000 \
    -l 2018
```

Adding the `-p, --partialSeason` flag will cause the program to limit simulation to completing the partial season provided.

```bash
java -jar build/libs/eplranking.jar \
    rank \
    -d ./datasets \
    -f 2000 \
    -l 2018 \
    -p ./datasets/2019-2020-partial.csv
```

#### Expected Outcome of Single Match

Displays a CSV table of match scorelines and their probability of occuring.

Adding the `-s, --summary` flag will produce a brief summary of expected results instead of a full CSV table of probabilities.

Adding the `-t, --transpose` flag will print the data transposed for creating a surface plot in Excel.

```bash
java -jar build/libs/eplranking.jar \
    compete \
    -d ./datasets \
    -f 2000 \
    -l 2018 \
    -h "Man City" \
    -a "Liverpool"
```

#### Season Result Table

Produces a CSV table of the highest probability outcomes for each match in a tournament of all teams present in the input data set.

```bash
java -jar build/libs/eplranking.jar \
    table \
    -d ./datasets \
    -f 2000 \
    -l 2018
```

Adding the `-p, --partialSeason` flag will cause the program to limit simulation to completing the partial season provided.

```bash
java -jar build/libs/eplranking.jar \
    table \
    -d ./datasets \
    -f 2000 \
    -l 2018 \
    -p ./datasets/2019-2020-partial.csv
```