package censusanalyser;

public class CSVBuilderFactory {

    private CSVBuilderFactory(){}

    public static ICSVBuilder createBuilder(){
        return new OpenCSVBuilder();
    }
}
