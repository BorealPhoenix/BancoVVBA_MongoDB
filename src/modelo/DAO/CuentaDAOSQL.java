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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CuentaDAOSQL implements CuentaDAO{
    private  static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static FindIterable<Document> iterDoc;
    private static Iterator it;
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
    public boolean eliminarCuentaBaseDatos(int id) {
        return false;
    }

    @Override
    public List<Cuenta> listarTodasLasCuentas() {
        List<Cuenta> cuentaList = new ArrayList<>();
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

   public static void main(String[] args) {
            CuentaDAO cuentaDAO = new CuentaDAOSQL();
            System.out.println(cuentaDAO.listarTodasLasCuentas());

   }

}
