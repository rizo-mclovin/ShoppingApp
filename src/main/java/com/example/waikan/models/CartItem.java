package com.example.waikan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cartitem")
@SuppressWarnings("all")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",nullable=false, updatable=false)
    private Product product;

}