package com.stockz.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Table(name="stocks")
@Entity
public class Stocks {
    @Id
    @Column
    private String stockid;

    @Column
    private String stockname;

    @Column
    private Double open;

    @Column
    private Double high;

    @Column
    private Double close;

    @Column
    private Double low;

    @Column
    private Double settlementprice;

    public Stocks() {

    }

    public Stocks(String stockid, String stockname, Double open, Double high, Double close, Double low, Double settlementprice) {
        this.stockid = stockid;
        this.stockname = stockname;
        this.open = open;
        this.high = high;
        this.close = close;
        this.low = low;
        this.settlementprice = settlementprice;
    }
}
