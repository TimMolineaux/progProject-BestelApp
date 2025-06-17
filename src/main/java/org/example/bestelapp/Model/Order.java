package org.example.bestelapp.Model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private LocalDate date;
    private List<OrderItem> items;

    public Order(int id, LocalDate date, List<OrderItem> items) {
        this.id = id;
        this.date = date;
        this.items = items;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public List<OrderItem> getItems() { return items; }
}
