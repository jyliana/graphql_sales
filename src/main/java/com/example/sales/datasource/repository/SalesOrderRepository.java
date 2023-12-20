package com.example.sales.datasource.repository;

import com.example.sales.datasource.entity.SalesOrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SalesOrderRepository extends JpaRepository<SalesOrderDto, UUID>, JpaSpecificationExecutor<SalesOrderDto> {
}
