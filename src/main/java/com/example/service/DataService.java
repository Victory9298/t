package com.example.service;

import com.example.model.Data;
import com.example.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    DataRepository dataRepository;
    public ResponseEntity<Data> getDataById(int id) {
        Optional<Data> foundData = dataRepository.findById(id);
        if (foundData.isPresent()) {
            return new ResponseEntity<>(foundData.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Data>> getTop5Data(String by) {
        List<Data> dataList;
       if (by.equals("death")) {
           dataList = dataRepository.findTop5CountriesByDeath();
           return new ResponseEntity<>(dataList, HttpStatus.OK);
       } else if (by.equals("active")){
           dataList = dataRepository.findTop5CountriesByActive();
           return new ResponseEntity<>(dataList, HttpStatus.OK);
        } else if (by.equals("recovered")) {
            dataList = dataRepository.findTop5CountriesByRecovered();
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<Integer> getTotalData(String by) {
        Integer totalNumber;
        if (by.equals("death")) {
            totalNumber = dataRepository.findTotalDeath();
            return new ResponseEntity<>(totalNumber, HttpStatus.OK);
        } else if (by.equals("active")){
            totalNumber = dataRepository.findTotalActive();
            return new ResponseEntity<>(totalNumber, HttpStatus.OK);
        } else if (by.equals("recovered")) {
            totalNumber = dataRepository.findTotalRecovered();
            return new ResponseEntity<>(totalNumber, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
