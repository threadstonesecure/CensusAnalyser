package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;
}
