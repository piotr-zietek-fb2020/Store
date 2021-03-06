package com.fb2020.Store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "issue_date")
    private Date issueDate;
    @Column(name = "total")
    private BigDecimal total;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Client client;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetails>invoiceDetails;

    public Invoice() {
    }

    public Invoice(Order order) {
        this.order = order;
        this.client = order.getClient();
        this.total = order.getTotalCost();
        this.issueDate = new Date(Calendar.getInstance().getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<InvoiceDetails> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
