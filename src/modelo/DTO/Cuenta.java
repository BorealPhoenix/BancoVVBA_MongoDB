package modelo.DTO;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Clase que define como es una cuenta, con sus atributos
 * getters, setters, toString, y equals y hashcode.
 */
public class Cuenta {
    //Atributos
    private ObjectId id;
    private String iban;
    private String creditCard;
    private double balance;
    private String fullName;
    private String date;

    //Constructor
    public Cuenta(ObjectId id, String iban, String creditCard, double balance, String fullName, String fecha) {
        this.id=id;
        this.iban = iban;
        this.creditCard = creditCard;
        this.balance = balance;
        this.fullName = fullName;
        this.date = fecha;
    }

    //Constructor vacio
    public Cuenta(String iban, String creditCard, double balance, String fullName, String date) {
        this.iban = iban;
        this.creditCard = creditCard;
        this.balance = balance;
        this.fullName = fullName;
        this.date = date;
    }


    //getters

    public ObjectId getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public double getBalance() {
        return balance;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDate() {
        return date;
    }

    //setters
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    //toString

    @Override
    public String toString() {
        return String.format("%s ,%s, %s, %.2f, %s, %s",id,
                iban, creditCard, balance, fullName, date);
    }

    //equals y hashccode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(iban, cuenta.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
