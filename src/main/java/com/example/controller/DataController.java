package com.example.controller;

import com.example.model.Data;
import com.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covid/")
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/{id}")
    public ResponseEntity<Data> getDataById(@PathVariable("id") int id) {
        return dataService.getDataById(id);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Data>> getTop5Data(@RequestParam("by") String by) {
        return dataService.getTop5Data(by);
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalData(@RequestParam("by") String by) {
        return dataService.getTotalData(by);
    }
}
