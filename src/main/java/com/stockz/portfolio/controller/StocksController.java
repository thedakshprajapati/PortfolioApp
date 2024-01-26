package com.stockz.portfolio.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stockz.portfolio.dto.ResponseMessage;
import com.stockz.portfolio.entity.Stocks;
import com.stockz.portfolio.repository.StocksRepository;
import com.stockz.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@RestController
@RequestMapping("/v1/stocks")
public class StocksController
{
//    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    @Autowired
    StocksRepository stocksRepository;

    @GetMapping("/")
    ResponseEntity<List<Stocks>> getStocks(){
//        Stocks newStock = new Stocks();
//        newStock.setStockId(543928L);
//        newStock.setStockName("Groww");
//        newStock.setOpen(100.0);
//        newStock.setClose(200.0);
//        newStock.setHigh(150.0);
//        newStock.setLow(50.0);
//        newStock.setSettlementPrice(130.0);
//
//        List<Stocks> obj = new ArrayList<>();
//        obj.add(newStock);

        try {
            List<Stocks> stocksList = stocksRepository.findAll();
            if(stocksList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(stocksList,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Stocks> getStock(@PathVariable("id") String stockid){
        try{
            Optional stockopt = stocksRepository.findById(stockid);
            if (stockopt.isEmpty()) {
                Stocks stock = (Stocks) stockopt.get();
                return new ResponseEntity<>(stock, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Stocks> postStocks(@RequestBody Stocks obj) {
        try {
            Stocks savedStock = stocksRepository.save(obj);
            return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stocks> putStock(@PathVariable("id") String stockid ,@RequestBody Stocks obj){
        try{
            Stocks updatedStock = stocksRepository.setStocksInfoById(stockid,obj.getStockname(),obj.getOpen(),obj.getHigh(),obj.getClose(),obj.getLow(),obj.getSettlementprice());
            return new ResponseEntity<>(updatedStock,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Stocks> deleteStock(@PathVariable("id") String stockid){
        try{
            stocksRepository.deleteById(stockid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch ( Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Stocks> deleteStocks(){
        try{
            stocksRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch ( Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

    }


    StockService stockService;

    @PostMapping("/uploadcsv")
    public ResponseEntity<ResponseMessage> csvUpload(@RequestBody MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File is empty."));
        }
        try {
            return stockService.getcsv(file);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage( "Failed to parse the csv."));
        }
    }

//    public ResponseEntity<ResponseMessage> putCSV(@RequestParam("file") MultipartFile file){
//        String message = "";
//
//        if (CSVHelper.hasCSVFormat(file)) {
//            try {
//                fileService.save(file);
//
//                message = "Uploaded the file successfully: " + file.getOriginalFilename();
//                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//            } catch (Exception e) {
//                e.printStackTrace();
//                e.getMessage();
//                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//            }
//        }
//
//        message = "Please upload a csv file!";
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
//    }

}
