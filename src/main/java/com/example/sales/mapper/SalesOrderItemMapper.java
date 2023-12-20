package com.example.sales.mapper;

import com.example.graphql.types.SalesOrderItem;
import com.example.graphql.types.SalesOrderItemInput;
import com.example.sales.datasource.entity.SalesOrderItemDto;

import java.util.UUID;

public class SalesOrderItemMapper {

  private SalesOrderItemMapper() {
  }

  public static SalesOrderItemDto mapToEntity(SalesOrderItemInput original) {
    var mapped = new SalesOrderItemDto();

    mapped.setModelUuid(UUID.fromString(original.getModelUuid()));
    mapped.setQuantity(original.getQuantity());

    return mapped;
  }

  public static SalesOrderItem mapToGraphqlEntity(SalesOrderItemDto original) {
    var mapped = new SalesOrderItem();

    mapped.setUuid(original.getUuid().toString());
    mapped.setModelUuid(original.getModelUuid().toString());
    mapped.setQuantity(original.getQuantity());

    return mapped;
  }

}
