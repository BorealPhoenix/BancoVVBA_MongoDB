package modelo.DAO;

import modelo.DTO.Cuenta;

import java.util.List;

public class CuentaDAOSQL implements CuentaDAO{

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
    public List<Cuenta> listarTodasLasCuentas(Cuenta cuenta) {
        return null;
    }
}
