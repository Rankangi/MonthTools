package fr.pcmaintenance.monthtools.Modele;

public class Transaction {
    private long date;
    private String pseudo;
    private String price;

    public Transaction(long date, String pseudo, String price){
        this.date = date;
        this.pseudo = pseudo;
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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
