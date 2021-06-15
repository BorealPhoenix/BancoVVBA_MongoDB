package Conexion;
import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Clase conexion a MongoDB con Patron Singlenton
 */
public class Conexion {
    private static Conexion conexion;
    private static MongoDatabase dataBase;

    /**
     * Llamamos a la conexion que tenemos en el puerto de nuestro ordenador
     * Seleccionamos el nombre que le hayamos dado a la base de dayos
     * Por ultimo cojemos la coleccion sobre la que queremos operar de nuestra base de datos
     */
    private Conexion() {
        MongoClient mongo = new MongoClient("localhost", 27017);
         dataBase = mongo.getDatabase("db1");
        MongoCollection<Document> collection = dataBase.getCollection("accounts");
    }

    /**
     * Getter de la base de datos
     * @return database
     */
    public  MongoDatabase getDataBase() {
        return dataBase;
    }

    /**
     * Comprobador de que solo se cree una conexion para acceder a la base de datos
     * @return conexion
     * @throws SQLException
     * @throws IOException
     */
    public static Conexion getInstance() throws SQLException, IOException {
        if (conexion == null) {
            conexion = new Conexion();
        }
        return conexion;
    }

    //Comprobacion de conexion realizada
   /*public static void main(String args[]) {
       MongoClient mongo = new MongoClient("localhost", 27017);
       MongoDatabase database = mongo.getDatabase("db1");
       MongoCollection<Document> collection = database.getCollection("accounts");
       FindIterable<Document> iterDoc = collection.find();
       Iterator it = iterDoc.iterator();
       while (it.hasNext()) {
           System.out.println(it.next());
       }
    }*/
}
