import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.internal.connection.MongoCredentialWithCache;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    //Conexion con patrón Singleton
    //Atributos
    private static Connection conexion;
    private static Conexion conexion5;

    //Creamos un constructor privado de Conexion
    //para que no pueda ser construida desde fuera

    private Conexion() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoCredential mongoCredential = MongoCredential.createCredential("" +
                "localhost", "MongoDB", "s3cret".toCharArray());
        MongoDatabase mongoDatabase = mongo.getDatabase("MongoDB");

    }
    //getter de connection

    public static Connection getConexion() {
        return conexion;
    }
        //acesso a conexion
    public static Conexion getInstance() {
        if (conexion5 == null) {
            conexion5 = new Conexion();
        }
        return conexion5;
    }


    //cierre de conexion
    static class HookCierreConexion extends Thread {
        @Override
        public void run() {
            try {
                Conexion conexion5 = new Conexion();
                Connection conexion = getConexion();
                if (conexion != null) {
                    conexion.close();
                    System.out.println("cerrada la conexión");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}