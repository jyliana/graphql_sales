package com.example.sales.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "addresses")
public class AddressDto {

  @Id
  @GeneratedValue
  private UUID uuid;
  private String street;
  private String city;
  private String zipcode;

}
