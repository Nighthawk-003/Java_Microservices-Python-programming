package com.example.TailorGang.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dress")
public class Model {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
@Column(name = "Dress name")
private String dressname;
@Column(name = "Dress description")
private String description;
@Column(name = "Price")
private float price;
 //Empty Constructor
public Model() {
}
public Model(String dressname, String description, float price) {
  this.dressname = dressname;
  this.description = description;
  this.price = price;
}
public long getId() {
  return id;
}
public String getDressname() {
  return dressname;
}
public void setDressname(String dressname) {
    this.dressname = dressname;
}
public String getDescription() {
  return description;
}
public void setDescription(String description) {
  this.description = description;
}
public float getPrice() {
    return price;
}
public void setPrice(float price) {
    this.price = price;
}
}
