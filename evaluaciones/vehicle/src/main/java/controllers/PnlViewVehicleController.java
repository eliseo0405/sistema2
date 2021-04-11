 package controllers;

import backend.dao.implementation.JVehicleDao;
import backend.pojo.Vehicle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import panels.PnlViewVehicles;


/**
 *
 * @author Elias Sirias
 */
public class PnlViewVehicleController implements Observer{

    private PnlViewVehicles pnlViewVehicles;
    private JVehicleDao jsonVehicleDaoImpl;
    private DefaultTableModel tblViewModel;
    private List<Vehicle> vehicles;
    private String[] HEADERS = new String[]{"StockNumber", "Year", "Make", "Model", "Style",
        "Vin", "Exterior color", "Interior color", "Miles", "Price", "Transmission", "Engine", "Image", "Status"};
    private TableRowSorter<DefaultTableModel> tblRowSorter;

    public PnlViewVehicleController(PnlViewVehicles pnlViewVehicles)
    {
        this.pnlViewVehicles = pnlViewVehicles;
        initComponent();
    }

    private void initComponent() 
    {
        try 
        {
            jsonVehicleDaoImpl = new JVehicleDao();

            loadTable();

            pnlViewVehicles.getTxtFinder().addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    txtFinderKeyTyped(e);
                }
            });

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txtFinderKeyTyped(KeyEvent e)
    {
        RowFilter<DefaultTableModel, Object> rf = null;
        rf = RowFilter.regexFilter(pnlViewVehicles.getTxtFinder().getText(), 0, 1, 2, 3, 4, 5, 6, 7, 8);
        tblRowSorter.setRowFilter(rf);
    }

    private void loadTable() throws IOException
    {
        tblViewModel = new DefaultTableModel(getData(), HEADERS);
        tblRowSorter = new TableRowSorter<>(tblViewModel);

        pnlViewVehicles.getTblViewVehicle().setModel(tblViewModel);
        pnlViewVehicles.getTblViewVehicle().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        pnlViewVehicles.getTblViewVehicle().setRowSorter(tblRowSorter);
    }

    private Object[][] getData() throws IOException
    {
        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
        Object data[][] = new Object[vehicles.size()][vehicles.get(0).asArray().length];

        IntStream.range(0, vehicles.size()).forEach(i ->
        {
            data[i] = vehicles.get(i).asArray();
        });
        
        return data;
    }

    public List<Vehicle> getVehicles()
    {
        return vehicles;
    }
    
    @Override
    public void update(Observable o, Object o1) {
       
       
    }
}
      