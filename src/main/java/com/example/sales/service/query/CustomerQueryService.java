package com.example.sales.service.query;

import com.example.graphql.types.UniqueCustomerInput;
import com.example.sales.datasource.entity.CustomerDto;
import com.example.sales.datasource.repository.CustomerRepository;
import com.example.sales.datasource.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class CustomerQueryService {

  @Autowired
  private CustomerRepository customerRepository;

  public Optional<CustomerDto> findUniqueCustomer(UniqueCustomerInput input) {
    var customerUuid = input.getUuid();
    var customerEmail = input.getEmail();

    if (isNoneBlank(customerUuid, customerEmail)) {
      throw new IllegalArgumentException("Only one parameter (customer UUID) OR customer email) allowed, not both");
    } else if (isAllBlank(customerUuid, customerEmail)) {
      throw new IllegalArgumentException("One of customer UUID OR customer email must exist");
    }

    var specification = isNotBlank(customerUuid) ?
            Specification.where(CustomerSpecification.uuidEqualsIgnoreCase(customerUuid)) :
            Specification.where(CustomerSpecification.emailEqualsIgnoreCase(customerEmail));

    return customerRepository.findOne(specification);
  }

  public Page<CustomerDto> findCustomers(Optional<UniqueCustomerInput> customer, Integer page, Integer size) {
    var pageable = PageRequest.of(
            Optional.ofNullable(page).orElse(0),
            Optional.ofNullable(size).orElse(20),
            Sort.by(CustomerSpecification.FIELD_EMAIL)
    );

    if (customer.isPresent()) {
      var existingCustomer = findUniqueCustomer(customer.get());

      return existingCustomer.map(customerDto -> new PageImpl<>(List.of(customerDto), pageable, 1)).orElseGet(() -> new PageImpl<>(Collections.emptyList(), pageable, 0));
    }

    return customerRepository.findAll(pageable);
  }

}
