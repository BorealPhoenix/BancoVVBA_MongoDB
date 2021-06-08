package modelo.DAO;

import Conexion.Conexion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modelo.DTO.Cuenta;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class CuentaDAOSQL implements CuentaDAO{
    private  static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static FindIterable<Document> iterDoc;
    private static Iterator it;
    private static  List<Cuenta> cuentaList = new ArrayList<>();
    static {
        try {
         //   System.out.println(Conexion.getInstance().getDataBase());
            database = Conexion.getInstance().getDataBase();
           collection = database.getCollection("accounts");
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
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
    public boolean eliminarCuentaBaseDatos(ObjectId id) {
        collection.find().forEach((Consumer<Document>) (Document document) ->
        {
         ObjectId idBaseDatos = document.getObjectId("_id");
         if (id == idBaseDatos){
             //eliminamos la cuenta
         }
        });

        return false;
    }

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

    public static List<Cuenta> settearIdCUenta(){
        List<Cuenta> cuentaListMapeada = new ArrayList<>();
        Map<Integer, ObjectId> map = new HashMap<>();

        for (int i = 0; i < cuentaList.size(); i++) {
            ObjectId id;

    //    cuentaList.get(i).setId();

        }

        return cuentaListMapeada;

    }
  /* public static void main(String[] args) {
            CuentaDAO cuentaDAO = new CuentaDAOSQL();
            System.out.println(cuentaDAO.listarTodasLasCuentas());

   }*/

}
