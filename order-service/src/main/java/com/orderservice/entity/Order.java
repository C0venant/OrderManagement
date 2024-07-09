package com.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"order\"")
@SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 10)
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_seq")
    @Column(updatable = false, nullable = false)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private String product;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        IN_PROGRESS,
        COMPLETED
    }
}
