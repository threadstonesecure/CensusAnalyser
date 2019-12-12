package censusanalyser;

public class CensusDAO<T extends CensusCSV> {

    public int population;
    public double densityPerSqKm;
    public double areaInSqKm;
    public String state;
    public String stateCode;

    public CensusDAO(T censusCSV){
        this.state = censusCSV.state;
        this.population = censusCSV.population;
        if(censusCSV instanceof IndiaCensusCSV){
            initializeIndiaCensusCSV((IndiaCensusCSV) censusCSV);
        }
        if(censusCSV instanceof USCensusCSV)
            initializeUSCensusCSV((USCensusCSV) censusCSV);
    }

    private void initializeIndiaCensusCSV(IndiaCensusCSV indiaCensusCSV){
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    private void initializeUSCensusCSV(USCensusCSV usCensusCSV){
        this.areaInSqKm = usCensusCSV.totalArea;
        this.densityPerSqKm = usCensusCSV.populationDensity;
        this.stateCode = usCensusCSV.stateId;
    }
}
