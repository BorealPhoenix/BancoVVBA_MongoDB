package vista;

import javax.swing.*;
import java.awt.*;

//Atributos
public class VistaCuentas {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JScrollPane centralPanel;
    private JLabel titleLabel;
    private JTable mainTable;
    private JButton buttonAdd;
    private JButton buttonDelete;

    //Getters de los botones
        public JButton getButtonAdd() {
            return buttonAdd;
        }

        public JButton getButtonDelete() {
            return buttonDelete;
        }

        public JTable getMainTable() {
            return mainTable;
        }

    //Arranque de la ventana principal
        public VistaCuentas (){
            JFrame frame = new JFrame();
            frame.setTitle("VVBA APP");
            frame.setContentPane(mainPanel);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(dimension.width / 2, dimension.height /2);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }
