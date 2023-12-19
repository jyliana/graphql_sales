package com.example.sales.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "customers")
public class CustomerDto {

  @Id
  @GeneratedValue
  private UUID uuid;

  @Column(unique = true)
  private String email;

  private LocalDate birthDate;
  private String fullName;
  private String phone;

  @OneToMany
  @JoinColumn(name = "customer_uuid")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  private List<AddressDto> addresses;

  @OneToMany
  @JoinColumn(name = "customer_uuid")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  private List<CustomerDocument> documents;

}
