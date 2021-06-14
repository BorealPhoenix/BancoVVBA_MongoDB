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
    private JButton buttonUpdate;
    private JPanel updatePanel;
    private JTextField ipdateTextFieldIBAN;
    private JLabel updateCreditCardLabel;
    private JTextField updateTextFieldCreditCard;
    private JLabel updateBalanceLabel;
    private JTextField updateTextFieldBalance;
    private JLabel updateNameLabel;
    private JTextField updateTextFieldName;
    private JLabel updateLabel;
    private JLabel addLabel;
    private JButton updateButtonCreate;
    private JButton updateButtonCancel;

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

        public JButton getButtonUpdate() {
            return buttonUpdate;
        }

    public JPanel getUpdatePanel() {
        return updatePanel;
    }

    public JTextField getIpdateTextFieldIBAN() {
        return ipdateTextFieldIBAN;
    }

    public JTextField getUpdateTextFieldCreditCard() {
        return updateTextFieldCreditCard;
    }

    public JTextField getUpdateTextFieldBalance() {
        return updateTextFieldBalance;
    }

    public JTextField getUpdateTextFieldName() {
        return updateTextFieldName;
    }

    public JButton getUpdateButtonCreate() {
        return updateButtonCreate;
    }

    public JButton getUpdateButtonCancel() {
        return updateButtonCancel;
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
