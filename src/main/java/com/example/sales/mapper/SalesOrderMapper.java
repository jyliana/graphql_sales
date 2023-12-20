package com.example.sales.mapper;

import com.example.graphql.types.AddSalesOrderInput;
import com.example.graphql.types.SalesOrder;
import com.example.sales.datasource.entity.SalesOrderDto;
import org.apache.commons.lang3.RandomStringUtils;

public class SalesOrderMapper {

  private SalesOrderMapper() {
  }

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

  public static SalesOrder mapToGraphqlEntity(SalesOrderDto original) {
    var mapped = new SalesOrder();
    var mappedFinance = FinanceMapper.mapToGraphqlEntity(original.getFinance());
    var mappedSalesOrderItems = original.getSalesOrderItems().stream()
            .map(SalesOrderItemMapper::mapToGraphqlEntity)
            .toList();

    mapped.setFinance(mappedFinance);
    mapped.setSalesOrderItems(mappedSalesOrderItems);
    mapped.setUuid(original.getUuid().toString());
    mapped.setOrderNumber(original.getOrderNumber());
    mapped.setOrderDateTime(original.getOrderDateTime());

    return mapped;
  }

  private static String generateOrderNumber() {
    return "SALES-" + RandomStringUtils.randomAlphabetic(8).toUpperCase();
  }

}
