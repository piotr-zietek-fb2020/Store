package com.fb2020.Store.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "total_cost")
    private double totalCost;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
