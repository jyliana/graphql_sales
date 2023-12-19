package com.example.sales.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "customer_documents")
public class CustomerDocument {

  @Id
  @GeneratedValue
  private UUID uuid;
  private String documentType;
  private String documentPath;
}
