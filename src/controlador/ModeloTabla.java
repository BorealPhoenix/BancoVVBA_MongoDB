package controlador;

import modelo.DAO.CuentaDAOSQL;
import modelo.DTO.Cuenta;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Clase encargada de asignar un modelo determinado a la tabla que sera
 * mostrada en la interfaz grafica
 */
public class ModeloTabla extends AbstractTableModel {
    private static final String [] COLUMNAS={"ID","IBAN", "CREDIT CARD", "BALANCE", "FULLNAME", "DATE"};
    private static final CuentaDAOSQL cuenta = new CuentaDAOSQL();
    private static List<Cuenta> listaCuentas;

    //Contructor
    public ModeloTabla() {
        listaCuentas = cuenta.listarTodasLasCuentas();
        fireTableDataChanged();
    }

    //Contador de filas
    @Override
    public int getRowCount() {
        return listaCuentas.size();
    }

    //Contador de columnas
    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    //Asignamos valores segun fila y columna
    @Override
    public Object getValueAt(int row, int column) {
            Object celda = null;
            switch (column) {
                case 0:
                    celda = listaCuentas.get(row).getId();
                    break;
                case 1:
                    celda = listaCuentas.get(row).getIban();
                    break;
                case 2:
                    celda = listaCuentas.get(row).getCreditCard();
                    break;
                case 3:
                    celda  = listaCuentas.get(row).getBalance();
                    break;
                case 4:
                    celda =listaCuentas.get(row).getFullName();
                    break;
                case 5:
                    celda =listaCuentas.get(row).getDate();
                    break;
            }

            return celda;
    }

    //Seteamos el nombre de las  columnas
    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

}
