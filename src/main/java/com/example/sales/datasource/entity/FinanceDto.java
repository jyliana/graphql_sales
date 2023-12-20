package com.example.sales.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.UUID;

@Entity
@Data
@Table(name = "finances")
public class FinanceDto {

  @Id
  @GeneratedValue
  private UUID uuid;

  private double baseAmount;
  private double taxAmount;
  private double discountAmount;
  private boolean isLoan;

  @OneToOne
  @JoinColumn(name = "sales_order_uuid")
  private SalesOrderDto salesOrder;

  @OneToOne(mappedBy = "financeDto")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private LoanDto loanDto;

}
