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
    private JPanel addPanel;
    private JLabel ibanLabel;
    private JTextField textFieldIBAN;
    private JLabel creditCardLabel;
    private JTextField textFieldcreditCard;
    private JLabel balanceLabel;
    private JTextField textFieldBalance;
    private JLabel fullNameLabel;
    private JTextField textFieldFullName;
    private JButton buttonCreate;
    private JButton buttonCancel;

    //Getters de los botones y de los TextField y el panel de añadir
        public JButton getButtonAdd() {
            return buttonAdd;
        }

        public JButton getButtonDelete() {
            return buttonDelete;
        }

        public JTable getMainTable() {
            return mainTable;
        }

        public JTextField getTextFieldIBAN() {
            return textFieldIBAN;
        }

        public JTextField getTextFieldcreditCard() {
            return textFieldcreditCard;
        }

        public JTextField getTextFieldBalance() {
            return textFieldBalance;
        }

        public JTextField getTextFieldFullName() {
            return textFieldFullName;
        }

        public JButton getButtonCreate() {
            return buttonCreate;
        }

        public JButton getButtonCancel() {
            return buttonCancel;
        }

        public JPanel getAddPanel() {
            return addPanel;
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
