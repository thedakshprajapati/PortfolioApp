package com.stockz.portfolio.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.stockz.portfolio.dto.ResponseMessage;
import com.stockz.portfolio.entity.Stocks;
import com.stockz.portfolio.repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

public class StockService {
    @Autowired
    StocksRepository stocksRepository;

    private int getNumberOfRows(MultipartFile file) throws IOException {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            int numRows = 0;
            while (csvReader.readNext() != null) {
                numRows++;
            }
            return numRows;
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<ResponseMessage> getcsv(MultipartFile file) throws IOException {
        Stocks stock = new Stocks();
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseMessage("File is empty"));
        }

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = csvReader.readNext();
            int numColumns = headers.length;
            String[][] csvData = new String[getNumberOfRows(file)][numColumns];
            String[] line;
            int rowNum = 0;
            while ((line = csvReader.readNext()) != null) {
                for (int colNum = 0; colNum < numColumns; colNum++) {
                    csvData[rowNum][colNum] = line[colNum];
                }
                rowNum++;
            }

            for (String[] row : csvData) {
                if(row[12]!=null) stock.setStockid(row[12]);
                else break;

                if(row[0]!=null) stock.setStockname(row[0]);
                else break;

                if(row[2]!=null) stock.setOpen(Double.parseDouble(row[2]));
                else break;

                if(row[5]!=null) stock.setClose(Double.parseDouble(row[5]));
                else break;

                if(row[3]!=null) stock.setHigh(Double.parseDouble(row[3]));
                else break;

                if(row[4]!=null) stock.setLow(Double.parseDouble(row[4]));
                else break;

                stock.setSettlementprice((Double.parseDouble(row[5]) + Double.parseDouble(row[4])/2));

                stocksRepository.save(stock);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully added data from CSV"));
        } catch (CsvValidationException e) {
//            throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage( "Failed to parse the csv."));
        }
    }
}
