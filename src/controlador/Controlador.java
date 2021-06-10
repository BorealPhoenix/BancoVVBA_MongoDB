package controlador;

import Conexion.Conexion;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import modelo.DAO.CuentaDAOSQL;
import modelo.DTO.Cuenta;
import org.bson.Document;
import org.bson.types.ObjectId;
import vista.VistaCuentas;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Controlador {
    //Atributos
    private ModeloTabla modelo;
    private VistaCuentas vista;
    private TableRowSorter<TableModel> sorter;
    private CuentaDAOSQL cuenta = new CuentaDAOSQL();
    private List<Cuenta> cuentasList= cuenta.listarTodasLasCuentas();
    private  static MongoDatabase database;
    private static MongoCollection<Document> collection;

    //Constructor
    public Controlador(ModeloTabla modeloTabla, VistaCuentas vista) {
        this.modelo = modeloTabla;
        this.vista = vista;
        registrarEventos();
        generarTabla();
    }

    static {
        try {
            //   System.out.println(Conexion.getInstance().getDataBase());
            database = Conexion.getInstance().getDataBase();
            collection = database.getCollection("accounts");
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void registrarEventos() {
        //Evento de Añadir cuenta
        vista.getButtonAdd().addActionListener(e->{
            vista.getAddPanel().setVisible(true);
          annadirCuenta();
        });


        //Evento de Borrado de cuenta
        vista.getButtonDelete().addActionListener(e->{
            int row = vista.getMainTable().getSelectedRow();
           ObjectId id= cuentasList.get(row).getId();
            cuenta.eliminarCuentaBaseDatos(id.toString());
        });
    }

    private void annadirCuenta() {
        vista.getButtonCancel().addActionListener(e->{
            vista.getTextFieldIBAN().setText("");
            vista.getTextFieldBalance().setText("");
            vista.getTextFieldcreditCard().setText("");
            vista.getTextFieldFullName().setText("");
            vista.getAddPanel().setVisible(false);
        });
        vista.getButtonCreate().addActionListener(e->{
            String iban = vista.getTextFieldIBAN().getText();
            String creditCard = vista.getTextFieldcreditCard().getText();
            Double balance = Double.parseDouble(vista.getTextFieldBalance().getText());
            String fullName= vista.getTextFieldFullName().getText();
            String date = String.format("%d-%d-%d",
                    LocalDate.now().getDayOfMonth(),
                    LocalDate.now().getMonthValue(),
                    LocalDate.now().getYear());

            //Añadimos a la lista de cuentas
            Cuenta cuenta2 = new Cuenta(iban, creditCard, balance
                    , fullName, date);
            cuenta.anadirCuentaABaseDatos(cuenta2);

            //Añadimos cuenta a la BD
     long lInicial= collection.countDocuments();
    Document document = new Document();
            // TODO: 10/6/21 METODO EN BUSQUEDA
     document.append("iban", iban);
     document.append("creditCard", creditCard);
     document.append("balance", balance);
     document.append("fullName", fullName);
     document.append("date", date);
     collection.insertOne(document);
     long lFinal=collection.countDocuments();

     if (lInicial-lFinal!=0){
         System.out.println("Account added to the DataBase Successfully");
     }
        });

    }

    //Generamos Tabla
    private void generarTabla() {
        sorter = new TableRowSorter<TableModel>(modelo);
        vista.getMainTable().setModel(modelo);
        vista.getMainTable().setRowSorter(sorter);
        vista.getAddPanel().setVisible(false);
    }


}
