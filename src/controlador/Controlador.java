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

    private void actualizarCuenta() {
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

            //Una vez se le da al boton crear, comprobamos algun dato halla cambiado
vista.getUpdateButtonCreate().addActionListener(actionEvent -> {
    String nuevoCreditCard = vista.getUpdateTextFieldCreditCard().getText();
    Double nuevoSaldo = Double.parseDouble(vista.getUpdateTextFieldBalance().getText());
    String nuevoNombre = vista.getUpdateTextFieldName().getText();
    if (nuevoNombre.equalsIgnoreCase(nombre)&&
        nuevoCreditCard.equalsIgnoreCase(creditCard)&&
            nuevoSaldo.equals(saldo)){
        JOptionPane.showMessageDialog(null, "Introduzca Datos Nuevos", "Data Warning",
                JOptionPane.WARNING_MESSAGE);
        return;
    }
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
        //Comprobacion añadido a la lista
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
    JOptionPane.showMessageDialog(null, "Cuenta actualizada con exito", "Update Confirmed",
            JOptionPane.INFORMATION_MESSAGE);
    vista.getUpdatePanel().setVisible(false);
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
            if (!vista.getTextFieldBalance().getText().equalsIgnoreCase("null")){
                JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos", "Adding Error",
                        JOptionPane.ERROR_MESSAGE);
                vista.getAddPanel().setVisible(false);
                controladorAnnadirCuentasVacias();
            }
            Double balance = Double.parseDouble(vista.getTextFieldBalance().getText());
            String fullName= vista.getTextFieldFullName().getText();
            String date = String.format("%d-%d-%d",
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonthValue(),
                    LocalDate.now().getDayOfMonth());

            //Controlamos que no esten los campos de texto vacios
            if (iban.equalsIgnoreCase("")||
                creditCard.equalsIgnoreCase("")||
                fullName.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos", "Adding Error",
                        JOptionPane.ERROR_MESSAGE);
                vista.getAddPanel().setVisible(false);
                controladorAnnadirCuentasVacias();
                return;
            }
            //Añadimos a la lista de cuentas
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

     if (lInicial-lFinal!=0){
         System.out.println("Account added to the DataBase Successfully");
     }
            vista.getAddPanel().setVisible(false);
            JOptionPane.showMessageDialog(null, "Cuenta añadida con exito", "Adding Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    modelo.fireTableDataChanged();

    }

    //Generamos Tabla
    private void generarTabla() {
        sorter = new TableRowSorter<TableModel>(modelo);
        vista.getMainTable().setModel(modelo);
        vista.getMainTable().setRowSorter(sorter);
        vista.getAddPanel().setVisible(false);
        vista.getUpdatePanel().setVisible(false);
    }


    private void controladorAnnadirCuentasVacias (){
        vista.getTextFieldIBAN().setText("");
        vista.getTextFieldBalance().setText("");
        vista.getTextFieldcreditCard().setText("");
        vista.getTextFieldFullName().setText("");
    }
}
