package com.example.shopapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "cart")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer cartId;

    private Double totalPrice;

    @OneToOne(mappedBy = "cart")
    private Customer customer;

    @CreationTimestamp
    @Column(columnDefinition = "datetime NOT NULL ")
    private Timestamp create_date;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp last_update;

    public Cart(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
