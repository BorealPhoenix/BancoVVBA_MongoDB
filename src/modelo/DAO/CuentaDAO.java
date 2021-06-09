package modelo.DAO;

import modelo.DTO.Cuenta;
import org.bson.types.ObjectId;

import java.util.List;

public interface CuentaDAO {
    boolean anadirCuentaABaseDatos();
    boolean modificarCuentaBaseDatos();
    boolean eliminarCuentaBaseDatos(String id);
    List<Cuenta> listarTodasLasCuentas ();
}
