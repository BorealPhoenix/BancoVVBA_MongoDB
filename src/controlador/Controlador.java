package controlador;

import Conexion.Conexion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import modelo.DAO.CuentaDAOSQL;
import modelo.DTO.Cuenta;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import vista.VistaCuentas;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase encargada de Controlar tanto la vista de la iterfaz como el modelo
 * de la tabla que listaremos.
 * Tambien tiene los metodo de actualizacion y añadir datos tanto a la BD como a la lista
 * Un registro de eventos encargado de los botones de la tabla y un generador de la propia tabla
 */
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

    /**
     * Conectamos con la base de datos de Mongo DB
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

    /**
     * Metodo encargado de controlar los botones principales de la vista
     * mediante actionListener: Boton Añadir, Boton Actualizar y Boton Borrar
     */
    private void registrarEventos() {
        //Evento de Añadir cuenta
        vista.getButtonAdd().addActionListener(e->{
            vista.getAddPanel().setVisible(true);
          annadirCuenta();
        });

        //Evento de Borrado de cuenta
        vista.getButtonDelete().addActionListener(e->{
            int row = vista.getMainTable().getSelectedRow();
            if (row>=0) {
                ObjectId id = cuentasList.get(row).getId();
                cuenta.eliminarCuentaBaseDatos(id.toString());
                JOptionPane.showMessageDialog(null, "Cuenta borrada con exito", "Delete Confirmed",
                        JOptionPane.INFORMATION_MESSAGE);

            }else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una linea para poder eliminarla", "Delete Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            modelo.fireTableDataChanged();
        });

        //Evento de actualizacion de cuenta
        vista.getButtonUpdate().addActionListener(e->{
            int row = vista.getMainTable().getSelectedRow();
            if (row>=0) {
                vista.getUpdatePanel().setVisible(true);
                actualizarCuenta();

            } else {
JOptionPane.showMessageDialog(null, "Debe seleccionar una linea para poder actualizarla", "Update Warning",
  JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Metodo que se ejecuta cada vez que se pulsa en boton de Actualizar, que
     * muestra los datos actualizables de la cuenta seleccionada,comprobando a
     * su vez que este seleccionada alguna cuenta.
     * Controla 2 botones, el de cancelar y el de añadir a la BD y a la lista
     */
    private void actualizarCuenta() {
        //Boton de cancelar: pone los campos de texto en blanco y oculta el panel
        vista.getUpdateButtonCancel().addActionListener(e->{
            vista.getUpdateTextFieldBalance().setText("");
            vista.getUpdateTextFieldCreditCard().setText("");
            vista.getUpdateTextFieldName().setText("");
            vista.getUpdatePanel().setVisible(false);
        });

        //Actualizacion de la cuenta en la lista
        int row = vista.getMainTable().getSelectedRow();
            Cuenta cuenta = cuentasList.get(row);
            //Mostramos los datos del objeto seleccionado en los campos de texto
        // Si no se verian en blanco, queda mejor poner el nombre del objeto seleccionado
            String creditCard = cuenta.getCreditCard();
            Double saldo = cuenta.getBalance();
            String nombre = cuenta.getFullName();
            vista.getUpdateTextFieldCreditCard().setText(creditCard);
            vista.getUpdateTextFieldBalance().setText(String.valueOf(saldo));
            vista.getUpdateTextFieldName().setText(nombre);

// Boton Crear: Una vez se le da, comprobamos algun dato halla cambiado
vista.getUpdateButtonCreate().addActionListener(actionEvent -> {
    String nuevoCreditCard = vista.getUpdateTextFieldCreditCard().getText();
    Double nuevoSaldo = Double.parseDouble(vista.getUpdateTextFieldBalance().getText());
    String nuevoNombre = vista.getUpdateTextFieldName().getText();

    //Si no ha cambiado ningun dato es que no se ha actualizado nada
    if (nuevoNombre.equalsIgnoreCase(nombre)&&
        nuevoCreditCard.equalsIgnoreCase(creditCard)&&
            nuevoSaldo.equals(saldo)){
        JOptionPane.showMessageDialog(null, "Introduzca Datos Nuevos", "Data Warning",
                JOptionPane.WARNING_MESSAGE);
        return;
    }
    //Si ha cambiado algun dato, pues los actualizamos
    else {
        //Actualizamos la lista
       cuenta.setCreditCard(nuevoCreditCard);
       cuenta.setBalance(nuevoSaldo);
       cuenta.setFullName(nuevoNombre);
       modelo.fireTableDataChanged();

       //Actualizamos la base de datos
        Bson filter = Filters.eq("_id", cuenta.getId());
        collection.updateOne(filter, Updates.combine(
                Updates.set("creditCard", nuevoCreditCard),
                Updates.set("balance", nuevoSaldo),
                Updates.set("fullName", nuevoNombre)
        ));

        //Comprobacion añadido a la lista ¿y la BD?
       if (!creditCard.equalsIgnoreCase(cuenta.getCreditCard())){
           System.out.println("Tarjeta de Credito Actualizada correctamente");
       }
        if (!nombre.equalsIgnoreCase(cuenta.getFullName())){
            System.out.println("Nombre Actualizado correctamente");
        }
        if (saldo != cuenta.getBalance()){
            System.out.println("Saldo Actualizado correctamente");
        }
    }

    //Mensaje de añadido correctamente y cerrar el panel
    JOptionPane.showMessageDialog(null, "Cuenta actualizada con exito", "Update Confirmed",
            JOptionPane.INFORMATION_MESSAGE);
    vista.getUpdatePanel().setVisible(false);
});

        }

    /**
     * Metodo que responde cuando le das al boton de añadir
     * Controla 2 botones a su vez, el boton de cancelar y
     * el boton de añadir a la BD y a la lista
     */
    private void annadirCuenta() {
        //BOTON CANCELAR: Campos de texto a cadena vacia y fuera panel
        vista.getButtonCancel().addActionListener(e->{
            seteadorCamposTextoACadenaVacia();
            vista.getAddPanel().setVisible(false);
        });

//BOTON CREAR: Vamos obteniendo los datos que nos hayan metido en los campos de texto
        vista.getButtonCreate().addActionListener(e->{
            String iban = vista.getTextFieldIBAN().getText();
            String creditCard = vista.getTextFieldcreditCard().getText();

//Controlamos que el saldo no este en blanco expresamente, al dar problemas en parseo
            if (!vista.getTextFieldBalance().getText().equalsIgnoreCase("null")){
                JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos", "Adding Error",
                        JOptionPane.ERROR_MESSAGE);
                vista.getAddPanel().setVisible(false);
                seteadorCamposTextoACadenaVacia();
            }
            Double balance = Double.parseDouble(vista.getTextFieldBalance().getText());
            String fullName= vista.getTextFieldFullName().getText();
            String date = String.format("%d-%d-%d",
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonthValue(),
                    LocalDate.now().getDayOfMonth());

            //Controlamos que no esten los campos de texto vacios en general
            if (iban.equalsIgnoreCase("")||
                creditCard.equalsIgnoreCase("")||
                fullName.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos", "Adding Error",
                        JOptionPane.ERROR_MESSAGE);
                vista.getAddPanel().setVisible(false);
                seteadorCamposTextoACadenaVacia();
                return;
            }
            //Añadimos cuenta a la lista de cuentas
            Cuenta cuenta2 = new Cuenta(iban, creditCard, balance
                    , fullName, date);
            cuenta.anadirCuentaABaseDatos(cuenta2);

            //Añadimos cuenta a la BD
     long lInicial= collection.countDocuments();
    Document document = new Document();

     document.append("iban", iban);
     document.append("creditCard", creditCard);
     document.append("balance", balance);
     document.append("fullName", fullName);
     document.append("date", date);
     collection.insertOne(document);
     long lFinal=collection.countDocuments();

//Si el tamaño inicial es distinto del tamaño final, es que ha cambiado la lista
     if (lInicial-lFinal!=0){
         System.out.println("Account added to the DataBase Successfully");
     }
            vista.getAddPanel().setVisible(false);
            JOptionPane.showMessageDialog(null, "Cuenta añadida con exito", "Adding Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        //Actualizamos la lista en la interfaz grafica con este metodo
    modelo.fireTableDataChanged();

    }

    /**
     * Metodo de generacion de la tabla
     * Controlamos los paneles que no queremos que sean mostrados de primeras
     */
    //Generamos Tabla
    private void generarTabla() {
        sorter = new TableRowSorter<TableModel>(modelo);
        vista.getMainTable().setModel(modelo);
        vista.getMainTable().setRowSorter(sorter);
        vista.getAddPanel().setVisible(false);
        vista.getUpdatePanel().setVisible(false);
    }

    /**
     * Metodo que setea los campos de texto a cadena vacia
     */
    private void seteadorCamposTextoACadenaVacia(){
        vista.getTextFieldIBAN().setText("");
        vista.getTextFieldBalance().setText("");
        vista.getTextFieldcreditCard().setText("");
        vista.getTextFieldFullName().setText("");
    }
}
