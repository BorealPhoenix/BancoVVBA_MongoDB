package modelo.DAO;

import Conexion.Conexion;
import modelo.DTO.Cuenta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAOSQL implements CuentaDAO{
    private Connection conexion = (Connection) Conexion.getInstance();

    public CuentaDAOSQL() throws SQLException, IOException {
    }


    @Override
    public boolean anadirCuentaABaseDatos(Cuenta cuenta) {
        return false;
    }

    @Override
    public boolean modificarCuentaBaseDatos(Cuenta cuenta) {
        return false;
    }

    @Override
    public boolean eliminarCuentaBaseDatos(int id) {
        return false;
    }

    @Override
    public List<Cuenta> listarTodasLasCuentas() {
        List<Cuenta> cuentaList = new ArrayList<>();
        String [] recogedor = conexion.toString().split("^=*,");
        String id = recogedor[0];
        String iban= recogedor[1];
        Double creditBalance= Double.parseDouble(recogedor[2]);
        Double balance= Double.parseDouble(recogedor[3]);
        String fullName= recogedor[4];
        String date = recogedor[5];
        Cuenta cuenta1 = new Cuenta(id, iban, creditBalance
        , balance, fullName, date);
        cuentaList.add(cuenta1);
        return cuentaList;
    }

   /* public static void main(String[] args) {
        try {
            CuentaDAO cuentaDAO = new CuentaDAOSQL();
            System.out.println(cuentaDAO.listarTodasLasCuentas());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
