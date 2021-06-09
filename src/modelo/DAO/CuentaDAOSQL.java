package modelo.DAO;

import Conexion.Conexion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import controlador.Controlador;
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
    public static Controlador controlador;
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
    public boolean anadirCuentaABaseDatos() {
        Document doc = new Document();
        Cuenta cuenta;
        return false;
    }

    @Override
    public boolean modificarCuentaBaseDatos() {
        return false;
    }

    @Override
    public boolean eliminarCuentaBaseDatos(String id) {
        for (Cuenta cuenta:cuentaList) {
            if (cuenta.getId().toString().equals(id)){
                cuentaList.remove(cuenta);
                System.out.println("Account deleted successfully...");
                collection.deleteOne(Filters.eq("_id", id));
                System.out.println("Document deleted successfully...");
                return true;
            }
        }

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

   /*public static void main(String[] args) {
            CuentaDAO cuentaDAO = new CuentaDAOSQL();
            System.out.println(cuentaDAO.listarTodasLasCuentas());

   }*/

}
