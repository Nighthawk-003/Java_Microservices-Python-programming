package com.example.TailorGang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TailorGang.model.Model;
import com.example.TailorGang.repository.Repository;

import java.util.*;

@RequestMapping("/api")
@RestController
public class Controller {

    @Autowired
    Repository myrepository;
  
  
   @PostMapping("/dress")
    public ResponseEntity<Model> createTutorial(@RequestBody Model model) {
      try {
        Model _tutorial = myrepository.save(new Model(model.getDressname(), model.getDescription(), model.getPrice()));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @GetMapping("/dress")
    public List<Model> getTutorial()
    {
      return myrepository.findAll();
    }
    @PutMapping("/plants/{id}")
    public ResponseEntity<Model> updateExpenses(@PathVariable("id") long id, @RequestBody Model model) {
    Optional<Model> expData = myrepository.findById(id);

    if (expData.isPresent()) {
      Model exp = expData.get();
      exp.setDressname(model.getDressname());
      exp.setDescription(model.getDescription());
      exp.setPrice(model.getPrice());
      return new ResponseEntity<>(myrepository.save(exp), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

    @DeleteMapping("/plants/{id}")
    public ResponseEntity<HttpStatus> deleteExpenses(@PathVariable("id") long id) {
      try {
        myrepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}