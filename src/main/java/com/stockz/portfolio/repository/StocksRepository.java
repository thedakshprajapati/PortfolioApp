package com.stockz.portfolio.repository;

import com.stockz.portfolio.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, String> {
    @Modifying
    @Query("update Stocks u set u.stockname = ?2, u.open = ?3, u.high = ?4, u.close = ?5, u.low = ?6, u.settlementprice = ?7 where u.stockid = ?1")
    Stocks setStocksInfoById(String stockid, String stockname, Double open, Double high, Double close, Double low, Double settlementprice);
}
