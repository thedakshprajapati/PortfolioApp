package com.stockz.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="user")
@Entity
@Getter
@Setter
public class User {

    @Id
    @Column
    private Long userid;

    @Column
    private String password;

    public User() {

    }
}
