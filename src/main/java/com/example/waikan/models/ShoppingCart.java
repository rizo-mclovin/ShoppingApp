package com.example.waikan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shoppingcart")
@SuppressWarnings("all")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private Double totalPrice;
    @Transient
    private int itemsNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<CartItem>();
    private String sessionToken;

}
