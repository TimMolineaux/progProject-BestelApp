package org.example.bestelapp.Model;

public class Product {
    private String categorie;
    private String productnaam;
    private int aantal;
    private boolean populair;

    // Getters en Setters
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getProductnaam() { return productnaam; }
    public void setProductnaam(String productnaam) { this.productnaam = productnaam; }

    public int getAantal() { return aantal; }
    public void setAantal(int aantal) { this.aantal = aantal; }

    public boolean isPopulair() { return populair; }
    public void setPopulair(boolean populair) { this.populair = populair; }
}
