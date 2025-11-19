package com.example.musicstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "InvoiceLine")
public class InvoiceLine {

    @Id
    @Column(name = "invoice_line_id")
    private Long id;

    @Column(name = "InvoiceId")
    private Long invoiceId;

    @Column(name = "TrackId")
    private Long trackId;

    @Column(name = "UnitPrice")
    private Double unitPrice;

    @Column(name = "Quantity")
    private Integer quantity;

    public InvoiceLine() {}
    public InvoiceLine(Long id, Long invoiceId, Long trackId, Double unitPrice, Integer quantity) {
        this.id = id; this.invoiceId = invoiceId; this.trackId = trackId;
        this.unitPrice = unitPrice; this.quantity = quantity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
