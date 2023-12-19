package com.example.sales.resolver;

import com.example.graphql.types.*;
import com.example.sales.service.command.CustomerCommandService;
import com.example.sales.service.query.CustomerQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class CustomerResolver {
  @Autowired
  private CustomerCommandService customerCommandService;

  @Autowired
  private CustomerQueryService customerQueryService;

  @DgsMutation
  public CustomerMutationResponse addNewCustomer(@InputArgument AddCustomerInput customer) {
    var saved = customerCommandService.addNewCustomer(customer);

    return CustomerMutationResponse.newBuilder()
            .customerUuid(saved.getUuid().toString())
            .success(true)
            .message(String.format("Customer %s saved", customer.getFullName()))
            .build();
  }

  @DgsMutation
  public CustomerMutationResponse addAddressesToExistingCustomer(@InputArgument UniqueCustomerInput customer, @InputArgument List<AddAddressInput> addresses) {
    var existingCustomer = customerQueryService.findUniqueCustomer(customer);
    if (existingCustomer.isEmpty()) {
      throw new DgsEntityNotFoundException(String.format("Customer : uuid %s / email %s not found", customer.getUuid(), customer.getEmail()));
    }
    customerCommandService.addAddressesToExistingCustomer(existingCustomer.get(), addresses);

    return CustomerMutationResponse.newBuilder()
            .customerUuid(existingCustomer.get().getUuid().toString())
            .success(true)
            .message(String.format("Added %d addresses to customer", addresses.size()))
            .build();
  }

  @DgsQuery
  public CustomerPagination customerPagination(
          @InputArgument Optional<UniqueCustomerInput> customer,
          DataFetchingEnvironment env,
          @InputArgument Integer page,
          @InputArgument Integer size
  ) {
    var pageCustomer = customerQueryService.findCustomers(
            customer, page, size
    );

    var pageConnection = new SimpleListConnection<>(
            pageCustomer.getContent()
    ).get(env);

    var paginatedResult = new CustomerPagination();

    paginatedResult.setCustomerConnection(pageConnection);
    paginatedResult.setPage(pageCustomer.getNumber());
    paginatedResult.setSize(pageCustomer.getSize());
    paginatedResult.setTotalElement(pageCustomer.getTotalElements());
    paginatedResult.setTotalPage(pageCustomer.getTotalPages());

    return paginatedResult;
  }

}
