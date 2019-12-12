package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH="./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_PATH);
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSCensusData_shouldReturnExactCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int count = 0;
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            count = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(51,count);
    }

    @Test
    public void givenIndiaStateCodeCSV_shouldReturnExactCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int count = 0;
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            count = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(37,count);
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedData() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String sortedCensusData = null;
        try {
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_PATH);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedIndianCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] indiaCensusCSVs = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",indiaCensusCSVs[0].state);
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedDataByArea() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_PATH);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        String sortedCensusDataByArea = censusAnalyser.getSortedIndiaCensusDataByArea();
        IndiaCensusCSV[] indiaCensusCSVs = new Gson().fromJson(sortedCensusDataByArea, IndiaCensusCSV[].class);
        Assert.assertEquals(342239, indiaCensusCSVs[0].areaInSqKm);
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedDataByPopulation() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_PATH);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        String sortedCensusDataByArea = censusAnalyser.getSortedIndiaCensusDataByPopulation();
        IndiaCensusCSV[] indiaCensusCSVs = new Gson().fromJson(sortedCensusDataByArea, IndiaCensusCSV[].class);
        Assert.assertEquals(240928, indiaCensusCSVs[0].areaInSqKm);
    }
}
