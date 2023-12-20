package com.example.sales.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "sales_order_items")
public class SalesOrderItemDto {

  @Id
  @GeneratedValue
  private UUID uuid;
  private int quantity;
  private UUID modelUuid;

}
