 package backend.dao.implementation;

import backend.dao.VehicleDao;
import backend.pojo.Vehicle;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Elias Sirias
 */

public class JVehicleDao extends RandomTemplate implements VehicleDao
{
    private final int SIZE = 800;
    private Gson gson;

    public JVehicleDao() throws FileNotFoundException
    {
        super(new File("vehicleJson.head"), new File("vehicleJson.dat"));
        this.gson = new Gson();
    }

    @Override
    public Vehicle findById(int id) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Vehicle> findByStatus(String status) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Vehicle t) throws IOException
    {
        getCustomRandom().getRafH().seek(0);
        int n = getCustomRandom().getRafH().readInt();
        int k = getCustomRandom().getRafH().readInt();
        
        long posD = k * SIZE;
        
        getCustomRandom().getRafD().seek(posD);
        
        getCustomRandom().getRafD().writeInt(++k);//id
        getCustomRandom().getRafD().writeUTF(gson.toJson(t));//Vehicle json
        
        long posH = 8 + (n * 8);
        getCustomRandom().getRafH().seek(posH);
        
        getCustomRandom().getRafH().writeInt(k);
        getCustomRandom().getRafH().write(t.getStockNumber());
        
        getCustomRandom().getRafH().seek(0);
        getCustomRandom().getRafH().writeInt(++n);
        getCustomRandom().getRafH().writeInt(k);
        
        close();
    }

    @Override
    public int update(Vehicle t) throws IOException
    {
        getCustomRandom().getRafH().seek(0);
        int n = getCustomRandom().getRafH().readInt();
        
        for(int i = 0; i < n; i++)
        {
            long posH = 8 + (i * 8);
            getCustomRandom().getRafH().seek(posH);
            
            int id = getCustomRandom().getRafH().readInt();
            
            if(id <= 0)
                continue;
            
            //System.out.println(id + " " + t.getId());
            
            if(id == t.getId())
            {
                long posD = (id - 1) * SIZE + 4;
                getCustomRandom().getRafD().seek(posD);
                getCustomRandom().getRafD().writeUTF(gson.toJson(t));//Vehicle json
                return 0;
            }
        }
        
        return -1;
    }
    
    @Override
    public boolean delete(Vehicle t) throws IOException
    {
        getCustomRandom().getRafH().seek(0);
        int n = getCustomRandom().getRafH().readInt();
        
        for(int i = 0; i < n; i++)
        {
            long posH = 8 + (i * 8);
            getCustomRandom().getRafH().seek(posH);
            
            int id = getCustomRandom().getRafH().readInt();
            
            if(id <= 0)
                continue;
            
            //System.out.println(id + " " + t.getId());
            
            if(id == t.getId())
            {
                getCustomRandom().getRafH().seek(posH);
                getCustomRandom().getRafH().writeInt(-1);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Vehicle> getAll() throws IOException
    {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle = null;
        
        getCustomRandom().getRafH().seek(0);
        int n = getCustomRandom().getRafH().readInt();
        
        for(int i = 0; i < n; i++)
        {
            long posH = 8 + (i * 8);
            getCustomRandom().getRafH().seek(posH);
            
            int id = getCustomRandom().getRafH().readInt();
            
            if(id <= 0)
                continue;
            
            long posD = (id - 1) * SIZE;
            getCustomRandom().getRafD().seek(posD);
            
            int vehicleId = getCustomRandom().getRafD().readInt();
            
            vehicle = gson.fromJson(getCustomRandom().getRafD().readUTF(), Vehicle.class);
            
            vehicle.setId(vehicleId);
            //System.out.println(vehicle.getId());
            
            vehicles.add(vehicle);
        }
        
        return vehicles;
    }
}
