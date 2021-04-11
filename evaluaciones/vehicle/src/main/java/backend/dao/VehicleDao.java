package backend.dao;

import backend.pojo.Vehicle;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Elias Sirias
 */
public interface VehicleDao extends Dao<Vehicle>
{
    Vehicle findById(int id) throws IOException;
    Collection<Vehicle> findByStatus(String status) throws IOException;
}
