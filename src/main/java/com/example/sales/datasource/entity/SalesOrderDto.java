package com.example.sales.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "sales_orders")
public class SalesOrderDto {
  @Id
  @GeneratedValue
  private UUID uuid;

  @ManyToOne
  @JoinColumn(name = "customer_uuid")
  private CustomerDto customer;

  @CreationTimestamp
  private ZonedDateTime orderDateTime;

  @Column(unique = true)
  private String orderNumber;

  @OneToMany
  @JoinColumn(name = "sales_order_uuid")
  @Fetch(FetchMode.SUBSELECT)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<SalesOrderItemDto> salesOrderItems;

  @OneToOne(mappedBy = "salesOrder")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private FinanceDto finance;
}
