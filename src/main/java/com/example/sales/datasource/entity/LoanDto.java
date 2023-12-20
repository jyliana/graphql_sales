package com.example.sales.datasource.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "loans")
public class LoanDto {

  @Id
  @GeneratedValue
  private UUID uuid;
  private String financeCompany;
  private String contactPersonName;
  private String contactPersonPhone;
  private String contactPersonEmail;

  @OneToOne
  @JoinColumn(name = "finance_uuid")
  private FinanceDto financeDto;

}
