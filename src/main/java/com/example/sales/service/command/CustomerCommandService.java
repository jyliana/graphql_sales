package com.example.sales.service.command;

import com.example.graphql.types.AddAddressInput;
import com.example.graphql.types.AddCustomerInput;
import com.example.sales.datasource.entity.CustomerDto;
import com.example.sales.datasource.repository.CustomerRepository;
import com.example.sales.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sales.mapper.CustomerMapper.mapToEntity;

@Service
public class CustomerCommandService {

  @Autowired
  private CustomerRepository customerRepository;

  public CustomerDto addNewCustomer(AddCustomerInput input) {
    var customerEntity = mapToEntity(input);

    return customerRepository.save(customerEntity);
  }

  public CustomerDto addAddressesToExistingCustomer(CustomerDto customer, List<AddAddressInput> addressList) {
    var addressesEntity = addressList.stream().map(AddressMapper::mapToEntity).toList();
    customer.getAddresses().addAll(addressesEntity);

    return customerRepository.save(customer);
  }

}
