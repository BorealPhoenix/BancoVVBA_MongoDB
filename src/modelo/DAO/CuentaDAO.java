package modelo.DAO;

import modelo.DTO.Cuenta;

import java.util.List;

public interface CuentaDAO {
    boolean anadirCuentaABaseDatos(Cuenta cuenta);
    boolean modificarCuentaBaseDatos(Cuenta cuenta);
    boolean eliminarCuentaBaseDatos(int id);
    List<Cuenta> listarTodasLasCuentas (Cuenta cuenta);
}
