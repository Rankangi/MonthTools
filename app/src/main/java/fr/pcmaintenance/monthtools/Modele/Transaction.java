package fr.pcmaintenance.monthtools.Modele;

public class Transaction {
    private int date;
    private String pseudo;
    private String price;

    public Transaction(int date, String pseudo, String price){
        this.date = date;
        this.pseudo = pseudo;
        this.price = price;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
