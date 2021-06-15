package modelo.DAO;

import Conexion.Conexion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import controlador.Controlador;
import controlador.ModeloTabla;
import modelo.DTO.Cuenta;
import org.bson.Document;
import org.bson.types.ObjectId;
import vista.VistaCuentas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Clase que implementa a la interfaz CuentaDAO, y que a su vez implementa sus metodos
 * Realmente solo es usado el metodo de borrar cuentas de la BD y la lista
 * Dado que el resto de metodos estan definidos en la Clase Controlador
 * Tambien posee el metodo que lista todas las cuentas de la base de datos de MongoDB
 */
public class CuentaDAOSQL implements CuentaDAO{
    //Atributos
    private  static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static FindIterable<Document> iterDoc;
    private static Iterator it;
    private static  List<Cuenta> cuentaList = new ArrayList<>();
    private static Controlador controlador;
    private static ModeloTabla modelo;

    /**
     * Arrancamos conexion con la BD
     */
    static {
        try {
         //   System.out.println(Conexion.getInstance().getDataBase());
            database = Conexion.getInstance().getDataBase();
           collection = database.getCollection("accounts");
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

//Metodo que no se si esta operativo que añade una cuenta a nuestra lista
    @Override
    public boolean anadirCuentaABaseDatos(Cuenta cuenta) {
      int lInicial=  cuentaList.size();
      cuentaList.add(cuenta);
        int lFinal=  cuentaList.size();
        if (lInicial-lFinal!=0){
            System.out.println("Added new account to the list successfully");
        }
        return lInicial-lFinal!=0;
    }

    //Metodo de modificar obsoletp
    @Override
    public boolean modificarCuentaBaseDatos() {
        return false;
    }

    /**
     * Metodo funcional de borrado de cuenta tanto en la lista como en la BD
     * @param id
     * @return true si lo borra, false si no
     */
    @Override
    public boolean eliminarCuentaBaseDatos(String id) {
        long inicial = collection.countDocuments();
        int listaInicial = cuentaList.size();
        long ultimo=0;

        //Comprobamos si el id pasado por parametro coincide con alguno de alguna cuenta
        for (Cuenta cuenta:cuentaList) {
            if (cuenta.getId().toString().equals(id)){

                //Borrado de la lista de cuentas
                cuentaList.remove(cuenta);
                int listaFinal= cuentaList.size();
                if (listaInicial-listaFinal!=0){
                    System.out.println("Eliminado correctamente de la lista de cuentas");
                } else System.out.println("Fallo al eliminar de la Lista");

                //Borrado de la BD
                collection.deleteOne(new Document("_id", new ObjectId(id)));
                ultimo = collection.countDocuments();
                if (inicial-ultimo!=0){
                    System.out.println("Eliminado Correctamente de la BD");
                } else System.out.println("Fallo al eliminar de la BD");
                break;
            }
        }
        return inicial-ultimo !=0;
    }

    /**
     * Metodo que lista todas las cuentas de la BD (usado como apoyo)
     * @return cuentaList, una lista con todas las cuentas
     */
    @Override
    public List<Cuenta> listarTodasLasCuentas() {

        collection.find().forEach((Consumer<Document>) (Document document) ->
        {
            ObjectId id = document.getObjectId("_id");
            String iban = document.getString("iban");
            String creditCard = document.getString("creditCard");
            Double balance = document.getDouble("balance");
            String fullName= document.getString("fullName");
            String date = document.getString("date");
            Cuenta cuenta = new Cuenta(id, iban, creditCard, balance, fullName,
                    date);
          //  System.out.println(cuenta);
            cuentaList.add(cuenta);
        });

        return cuentaList;
    }

}
