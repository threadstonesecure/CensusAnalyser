package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    private Map<String, CensusDAO> censusMapData;
    private Map<String, IndiaStateCodeCSV> stateCodeMap;

    public CensusAnalyser(){
        this.censusMapData = new TreeMap<>();
        this.stateCodeMap = new TreeMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        List<IndiaStateCodeCSV> indiaStateCodeCSVs = loadCSVData(csvFilePath, IndiaStateCodeCSV.class);
        indiaStateCodeCSVs.stream().forEach(indiaStateCodeCSV -> stateCodeMap.put(indiaStateCodeCSV.stateName, indiaStateCodeCSV));
        return indiaStateCodeCSVs.size();
    }

    public String getSortedIndianCensusData() {
        return new Gson().toJson(censusMapData.values());
    }

    public String getSortedIndiaCensusDataByArea() {
        List<CensusDAO> sortedCensusData = censusMapData.values().stream()
                .sorted(Comparator.comparing(censusData -> censusData.areaInSqKm))
                    .collect(Collectors.toList());
        Collections.reverse(sortedCensusData);
        return new Gson().toJson(sortedCensusData);
    }

    public String getSortedIndiaCensusDataByPopulation() {
        List<CensusDAO> sortedCensusData = censusMapData.values().stream()
                .sorted(Comparator.comparing(censusData -> censusData.population))
                .collect(Collectors.toList());
        Collections.reverse(sortedCensusData);
        return new Gson().toJson(sortedCensusData);
    }

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyserException {
          return loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
    }

    private int loadCensusData(String csvFilePath, Class<? extends CensusCSV> csvClass) throws CensusAnalyserException {
        List<? extends CensusCSV> censusCSVs = loadCSVData(csvFilePath, csvClass);
        censusCSVs.stream()
                .forEach(censusCSV -> {
            CensusDAO censusDAO = new CensusDAO(censusCSV);
            if(censusDAO.stateCode == null)
                censusDAO.stateCode = stateCodeMap.get(censusCSV.state).stateCode;
            censusMapData.put(censusCSV.state, censusDAO);
        });
        return censusCSVs.size();
    }

    private <E> List<E> loadCSVData(String csvFilePath, Class<E> csvClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createBuilder();
            return csvBuilder.getList(reader,csvClass);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}