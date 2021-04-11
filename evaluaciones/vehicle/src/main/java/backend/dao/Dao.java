package backend.dao;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Elias Sirias
 */
public interface Dao<T>
{
    void create(T t) throws IOException;
    int update(T t) throws IOException;
    boolean delete(T t) throws IOException;
    Collection<T> getAll() throws IOException;
}
