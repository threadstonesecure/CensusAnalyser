package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV extends CensusCSV{

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;
}
