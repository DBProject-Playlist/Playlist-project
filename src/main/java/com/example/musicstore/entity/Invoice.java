package com.example.musicstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @Column(name = "invoice_id")
    private Long id;

    @Column(name = "CustomerId")
    private Long customerId;

    @Column(name = "Total")
    private Double total;

    public Invoice() {}
    public Invoice(Long id, Long customerId, Double total) {
        this.id = id; this.customerId = customerId; this.total = total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
