package com.example.sales.resolver;

import com.example.graphql.types.AddSalesOrderInput;
import com.example.graphql.types.SalesOrderMutationResponse;
import com.example.sales.service.command.SalesOrderCommandService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class SalesOrderResolver {

  @Autowired
  private SalesOrderCommandService salesOrderCommandService;

  @DgsMutation
  public SalesOrderMutationResponse addNewSalesOrder(@InputArgument AddSalesOrderInput salesOrder) {
    var saved = salesOrderCommandService.addNewSalesOrder(salesOrder);

    return SalesOrderMutationResponse.newBuilder()
            .customerUuid(salesOrder.getCustomerUuid())
            .salesOrderUuid(saved.getUuid().toString())
            .orderNumber(saved.getOrderNumber())
            .success(true)
            .message(String.format("New Sales Order %s created", saved.getOrderNumber()))
            .build();
  }

}
