package censusanalyser;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilder {

    <E> Iterable<E> getIterable(Reader reader, Class<E> beanClass) throws CSVBuilderException;

    <E> List<E> getList(Reader reader, Class<E> beanClass) throws CSVBuilderException;
}
