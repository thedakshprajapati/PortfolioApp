package com.stockz.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "holding")
@Getter
@Setter
@NoArgsConstructor
public class Holding {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long holdingid;

    @Column
    private Long userid;

    @Column
    private String type;

    @Column
    private Long quantity;

    @Column
    private Long stockid;
}
