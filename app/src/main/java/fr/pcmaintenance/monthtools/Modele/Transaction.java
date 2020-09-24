package fr.pcmaintenance.monthtools.Modele;

public class Transaction implements Comparable<Transaction>{
    private String date;
    private String pseudo;
    private String price;
    private int id;

    public Transaction(String date, String pseudo, String price, int id){
        this.date = date;
        this.pseudo = pseudo;
        this.price = price;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Transaction transaction) {
        int  compareQuantity = transaction.getId();
        return this.id - compareQuantity;
    }
}
