package com.example.sales.mapper;


import com.example.graphql.types.AddAddressInput;
import com.example.graphql.types.Address;
import com.example.sales.datasource.entity.AddressDto;

public class AddressMapper {

  public static AddressDto mapToEntity(AddAddressInput original) {
    var mapped = new AddressDto();

    mapped.setStreet(original.getStreet());
    mapped.setCity(original.getCity());
    mapped.setZipcode(original.getZipcode());

    return mapped;
  }

  public static Address mapToGraphqlEntity(AddressDto original) {
    var mapped = new Address();

    mapped.setUuid(original.getUuid().toString());
    mapped.setCity(original.getCity());
    mapped.setStreet(original.getStreet());
    mapped.setZipcode(original.getZipcode());

    return mapped;
  }
}
