package controlador;

import modelo.DAO.CuentaDAOSQL;
import modelo.DTO.Cuenta;
import org.bson.types.ObjectId;
import vista.VistaCuentas;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class Controlador {
    //Atributos
    private ModeloTabla modelo;
    private VistaCuentas vista;
    private TableRowSorter<TableModel> sorter;
    private CuentaDAOSQL cuenta = new CuentaDAOSQL();
    private List<Cuenta> cuentasList= cuenta.listarTodasLasCuentas();
    //Constructor

    public Controlador(ModeloTabla modeloTabla, VistaCuentas vista) {
        this.modelo = modeloTabla;
        this.vista = vista;
        registrarEventos();
        generarTabla();
    }


    private void registrarEventos() {
        vista.getButtonAdd().addActionListener(e->{
          cuenta.anadirCuentaABaseDatos();
        });
        vista.getButtonDelete().addActionListener(e->{
            int row = vista.getMainTable().getSelectedRow();
           ObjectId id= cuentasList.get(row).getId();
            cuenta.eliminarCuentaBaseDatos(id.toString());
        });
    }

    //Generamos Tabla
    private void generarTabla() {
        sorter = new TableRowSorter<TableModel>(modelo);
        vista.getMainTable().setModel(modelo);
        vista.getMainTable().setRowSorter(sorter);
//        vista.getRightPanel().setVisible(false);
    }
}
