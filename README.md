# INFO 6205 Final Project

This application is a ranking system capable of predicting league standings for the English Premier League based on past match outcomes.

## Running Tests
```
./gradlew test
```

## Building the Application
```
./gradlew shadowjar
```

## Running the Program

### Show Rankings Based on Historic Data
```bash
java -jar build/libs/eplranking.jar \
    -dataDir ./datasets \
    -firstSeasonStartYear 2008 \
    -lastSeasonStartYear 2010 \
    -rank
```
