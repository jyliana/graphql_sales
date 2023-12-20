package com.example.sales.mapper;

import com.example.graphql.types.AddSalesOrderInput;
import com.example.sales.datasource.entity.SalesOrderDto;
import org.apache.commons.lang3.RandomStringUtils;

public class SalesOrderMapper {

  public static SalesOrderDto mapToEntity(AddSalesOrderInput original) {
    var mapped = new SalesOrderDto();

    mapped.setOrderNumber(generateOrderNumber());

    var mappedFinance = FinanceMapper.mapToEntity(original.getFinance());
    mappedFinance.setSalesOrder(mapped);
    mapped.setFinance(mappedFinance);

    var salesOrderItemsEntity = original.getSalesOrderItems().stream()
            .map(SalesOrderItemMapper::mapToEntity)
            .toList();

    mapped.setSalesOrderItems(salesOrderItemsEntity);

    return mapped;
  }

  private static String generateOrderNumber() {
    return "SALES-" + RandomStringUtils.randomAlphabetic(8).toUpperCase();
  }

}
