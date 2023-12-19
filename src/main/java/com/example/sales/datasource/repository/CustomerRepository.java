package com.example.sales.datasource.repository;

import com.example.sales.datasource.entity.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerDto, UUID>,
        JpaSpecificationExecutor<CustomerDto> {
}
