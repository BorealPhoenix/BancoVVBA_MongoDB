package modelo.DAO;

import modelo.DTO.Cuenta;
import org.bson.types.ObjectId;

import java.util.List;

public interface CuentaDAO {
    boolean anadirCuentaABaseDatos(Cuenta cuenta);
    boolean modificarCuentaBaseDatos(Cuenta cuenta);
    boolean eliminarCuentaBaseDatos(ObjectId id);
    List<Cuenta> listarTodasLasCuentas ();
}
