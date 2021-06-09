package controlador;

import modelo.DAO.CuentaDAOSQL;
import modelo.DTO.Cuenta;
import vista.VistaCuentas;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Controlador {
    //Atributos
    private ModeloTabla modelo;
    private VistaCuentas vista;
    private TableRowSorter<TableModel> sorter;

    //Constructor

    public Controlador(ModeloTabla modeloTabla, VistaCuentas vista) {
        this.modelo = modeloTabla;
        this.vista = vista;
        registrarEventos();
        generarTabla();
    }


    private void registrarEventos() {
        vista.getButtonAdd().addActionListener(e->{
        //   cuenta.anadirCuentaABaseDatos();
        });
        vista.getButtonDelete().addActionListener(e->{
            int row = vista.getMainTable().getSelectedRow();
          //  cuenta.eliminarCuentaBaseDatos(String.valueOf(row));
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
