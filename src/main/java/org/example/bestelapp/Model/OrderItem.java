package org.example.bestelapp.Model;

public class OrderItem {
    private String productnaam;
    private int aantal;

    public OrderItem(String productnaam, int aantal) {
        this.productnaam = productnaam;
        this.aantal = aantal;
    }

    public String getProductnaam() { return productnaam; }
    public int getAantal() { return aantal; }
}
