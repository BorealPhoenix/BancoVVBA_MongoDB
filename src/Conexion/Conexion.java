package Conexion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import java.sql.Connection;


public class Conexion {
    private static Conexion conexion;
    private Connection connection;

    public Conexion() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase baseDeDatos =  mongoClient.getDatabase("db1");
        MongoCollection collection = baseDeDatos.getCollection("accounts");
        System.out.println(collection);
    }

    public Connection getConexion() {
        return connection;
    }

        public static void main(String[] args) {

            Conexion conexion = new Conexion();
            System.out.println(conexion);
            System.out.println(conexion.getConexion());

        }
    }

