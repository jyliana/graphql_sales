package com.example.sales.service.command;

import com.example.graphql.types.AddSalesOrderInput;
import com.example.sales.datasource.entity.SalesOrderDto;
import com.example.sales.datasource.repository.CustomerRepository;
import com.example.sales.datasource.repository.SalesOrderRepository;
import com.example.sales.mapper.SalesOrderMapper;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SalesOrderCommandService {

  @Autowired
  private SalesOrderRepository salesOrderRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public SalesOrderDto addNewSalesOrder(AddSalesOrderInput addSalesOrderInput) {
    var existingCustomer = customerRepository.findById(
            UUID.fromString(addSalesOrderInput.getCustomerUuid()));

    if (existingCustomer.isEmpty()) {
      throw new DgsEntityNotFoundException(
              String.format("Customer UUID %s not found", addSalesOrderInput.getCustomerUuid()));
    }

    var salesOrderEntity = SalesOrderMapper.mapToEntity(addSalesOrderInput);
    salesOrderEntity.setCustomer(existingCustomer.get());

    return salesOrderRepository.save(salesOrderEntity);
  }
}
