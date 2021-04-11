 package panels;

import backend.dao.implementation.JVehicleDao;
import backend.pojo.Vehicle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Elias Sirias
 */
public class PnlVehicleTable extends javax.swing.JPanel
{
    JTextField TextoClave;
    TableModel Model;
    JTable Table;

    public PnlVehicleTable()
    {
        Model = new ModeloTabla();
        setBounds(200, 300, 1200, 400);
        Table = new JTable(Model);
        setLayout(new BorderLayout());
        Table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        
        add(new JScrollPane(Table), BorderLayout.CENTER);
        JButton botonImprimir = new JButton("Imprimir tabla");
        
        botonImprimir.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Table.print();
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        JPanel pnlBotonInferior = new JPanel();
        pnlBotonInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBotonInferior.add(botonImprimir);
        
        add(pnlBotonInferior, BorderLayout.SOUTH);
        
        //creo un panel superior para un text field
        JPanel pnlSuperior = new JPanel();
        
        pnlSuperior.setLayout(new GridBagLayout());
        //Establesco los Constraints del GridBagLayout pnlSuperior
        GridBagConstraints gridBadConstraints = new GridBagConstraints();
        gridBadConstraints.fill = GridBagConstraints.BOTH;
        gridBadConstraints.weightx = 0.1;
        gridBadConstraints.insets = new Insets(1, 1, 1, 1);
        
        TextoClave = new JTextField();
        TextoClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPalabraClaveKeyReleased(evt);
            }
        });
        
        //agrego el JTextField y el GridBadConstraints al panel superior
        pnlSuperior.add(TextoClave, gridBadConstraints);
        add(pnlSuperior, BorderLayout.NORTH);
        
    }
    
    private void filter(String busquedad)
    {
        TableRowSorter<ModeloTabla> tr = new TableRowSorter<ModeloTabla>((ModeloTabla) Model);
        Table.setRowSorter(tr);
        
        tr.setRowFilter(RowFilter.regexFilter(busquedad));
    }
    
    private void txtPalabraClaveKeyReleased(KeyEvent event)
    {
        String Qr = TextoClave.getText();
        filter(Qr);
    }
    
    public JTextField getTextoClave()
    {
        return TextoClave;
    }

    public void setTextoClave(JTextField TextoClave)
    {
        this.TextoClave = TextoClave;
    }
    
}

class ModeloTabla extends AbstractTableModel
{
    private JVehicleDao JAVAD;
    List<Vehicle> VH;
    
    private void init() throws FileNotFoundException, IOException
    {
        JAVAD = new JVehicleDao();
        VH = (List<Vehicle>) JAVAD.getAll();
    }
    
    private int getVehicleNumber()
    {
        int number;
        try
        {
            init();
            number = VH.size();
            return number;
            
        } catch (IOException ex)
        {
            Logger.getLogger(ModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0; 
    }

    @Override
    public int getRowCount()
    {
        return getVehicleNumber();
    }

    @Override
    public int getColumnCount()
    {
        return 14;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Vehicle Vh = VH.get(rowIndex);
        switch(columnIndex)
        {
            case 0:
                return Vh.getStockNumber();
            case 1:
                return Vh.getYear();
            case 2:
                return Vh.getMake();
            case 3:
                return Vh.getModel();
            case 4:
                return Vh.getStyle();
            case 5:
                return Vh.getVin();
            case 6:
                return Vh.getExteriorColor();
            case 7:
                return Vh.getInteriorColor();
            case 8:
                return Vh.getMiles();
            case 9:
                return Vh.getPrice();
            case 10:
                return Vh.getTransmission();
            case 11:
                return Vh.getEngine();
            case 12:
                return Vh.getImage();
            case 13:
                return Vh.getStatus();
        }
        return null;
    }
    
    @Override
    public String getColumnName(int c)
    {
        switch(c)
        {
            case 0:
                return "StockNumber";
            case 1:
                return "Year";
            case 2:
                return "Make";
            case 3:
                return "Model";
            case 4:
                return "Style";
            case 5:
                return "Vin";
            case 6:
                return "Exetrior Color";
            case 7:
                return "Interior Color";
            case 8:
                return "Miles";
            case 9:
                return "Price";
            case 10:
                return "Transmission";
            case 11:
                return "Engene";
            case 12:
                return "Image";
            case 13:
                return "Status";
        }
        return "Columna " +c;
    }
}
