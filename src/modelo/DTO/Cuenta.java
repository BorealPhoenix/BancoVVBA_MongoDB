package modelo.DTO;

import java.util.Objects;

public class Cuenta {
    //Atributos
    private String iban;
    private double creditCard;
    private double balance;
    private String fullName;
    private String date;

    //Constructor
    public Cuenta(String iban, double creditCard, double balance, String fullName, String fecha) {
        this.iban = iban;
        this.creditCard = creditCard;
        this.balance = balance;
        this.fullName = fullName;
        this.date = fecha;
    }

    //Constructor vacio
    public Cuenta() {
    }

    //getters

    public String getIban() {
        return iban;
    }

    public double getCreditCard() {
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

    //toString

    @Override
    public String toString() {
        return String.format("%s, %.2f, %.2f, %s, %s",
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