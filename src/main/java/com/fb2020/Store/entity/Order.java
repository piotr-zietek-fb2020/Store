package com.fb2020.Store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

import java.sql.Date;
import java.util.List;


@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
    @JsonIgnore
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Invoice invoice;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
