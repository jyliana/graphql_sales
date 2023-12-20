package com.example.sales.resolver;

import com.example.graphql.DgsConstants;
import com.example.graphql.types.*;
import com.example.sales.mapper.CustomerMapper;
import com.example.sales.service.command.CustomerCommandService;
import com.example.sales.service.query.CustomerQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class CustomerResolver {

  private CustomerCommandService customerCommandService;
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
    var pageCustomer = customerQueryService.findCustomers(customer, page, size);

    var listCustomerAsEntity = Optional.of(pageCustomer.getContent()).orElse(Collections.emptyList());
    var listCustomerAsGraphql = listCustomerAsEntity.stream().map(CustomerMapper::mapToGraphqlEntity).toList();

    var paginatedResult = new CustomerPagination();
    var pageConnection = new SimpleListConnection<>(listCustomerAsGraphql).get(env);

    paginatedResult.setCustomerConnection(pageConnection);
    paginatedResult.setPage(pageCustomer.getNumber());
    paginatedResult.setSize(pageCustomer.getSize());
    paginatedResult.setTotalElement(pageCustomer.getTotalElements());
    paginatedResult.setTotalPage(pageCustomer.getTotalPages());

    return paginatedResult;
  }

  @DgsMutation
  public CustomerMutationResponse addDocumentToExistingCustomer(
          @InputArgument UniqueCustomerInput customer,
          @InputArgument String documentType,
          DataFetchingEnvironment env
  ) {
    var existingCustomer = customerQueryService.findUniqueCustomer(customer);

    if (existingCustomer.isEmpty()) {
      throw new DgsEntityNotFoundException(
              String.format("Customer: uuid %s / email %s not found", customer.getUuid(), customer.getEmail()));
    }

    MultipartFile documentFile = env.getArgument(DgsConstants.MUTATION.ADDDOCUMENTTOEXISTINGCUSTOMER_INPUT_ARGUMENT.DocumentFile);

    customerCommandService.addDocumentToExistingCustomer(existingCustomer.get(), documentType, documentFile);

    return CustomerMutationResponse.newBuilder()
            .customerUuid(existingCustomer.get().getUuid().toString())
            .success(true)
            .message(documentFile.getOriginalFilename() + " uploaded")
            .build();
  }

  @DgsMutation
  public CustomerMutationResponse updateExistingCustomer(@InputArgument UniqueCustomerInput customer,
                                                         @InputArgument UpdateCustomerInput customerUpdate) {
    if (StringUtils.isAllBlank(customerUpdate.getEmail(), customerUpdate.getPhone())) {
      throw new IllegalArgumentException("Customer update must not empty");
    }

    var existingCustomer = customerQueryService.findUniqueCustomer(customer);

    if (existingCustomer.isEmpty()) {
      throw new DgsEntityNotFoundException(
              String.format("Customer: uuid %s / email %s not found", customer.getUuid(), customer.getEmail()));
    }

    customerCommandService.updateCustomer(existingCustomer.get(), customerUpdate);

    return CustomerMutationResponse.newBuilder()
            .customerUuid(existingCustomer.get().getUuid().toString())
            .success(true)
            .message("Customer updated")
            .build();
  }

}
