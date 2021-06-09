import controlador.Controlador;
import controlador.ModeloTabla;
import vista.VistaCuentas;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaCuentas vista = new VistaCuentas();
                ModeloTabla modelo = new ModeloTabla();
                new Controlador(modelo, vista);
            }
        });
    }
}