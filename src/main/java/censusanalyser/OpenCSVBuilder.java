package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {

    public <E> Iterable<E> getIterable(Reader reader, Class<E> beanClass) throws CSVBuilderException {
        Iterator<E> censusCSVIterator = getCsvToBean(reader, beanClass).iterator();
        return () -> censusCSVIterator;
    }

    public <E> List<E> getList(Reader reader, Class<E> beanClass) throws CSVBuilderException {
        return getCsvToBean(reader, beanClass).parse();
    }

    private <E> CsvToBean<E> getCsvToBean(Reader reader, Class<E> beanClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(beanClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
