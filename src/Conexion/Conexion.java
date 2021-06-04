package Conexion;
import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;


public class Conexion {
    private static Conexion conexion;
    private Connection dataBase;
//Usar literable:

    private Conexion() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("db1");
        MongoCollection<Document> collection = database.getCollection("accounts");
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public  Connection getConexion() {
        return dataBase;
    }

    public static Conexion getInstance() throws SQLException, IOException {
        if (conexion == null) {
            conexion = new Conexion();
        }
        return conexion;
    }

   /* public static void main(String args[]) {
        Conexion conexion = new Conexion();
        System.out.println(conexion);
    }*/
}
