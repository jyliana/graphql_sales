package com.example.sales.mapper;


import com.example.graphql.types.AddCustomerInput;
import com.example.graphql.types.Customer;
import com.example.sales.datasource.entity.CustomerDto;
import com.example.sales.mapper.AddressMapper;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;

public class CustomerMapper {

    public static CustomerDto mapToEntity(AddCustomerInput original) {
        var mapped = new CustomerDto();

        mapped.setBirthDate(original.getBirthDate());
        mapped.setEmail(original.getEmail());
        mapped.setFullName(original.getFullName());
        mapped.setPhone(original.getPhone());

        if (!CollectionUtils.isEmpty(original.getAddresses())) {
            var addressesEntity = original.getAddresses()
                    .stream().map(AddressMapper::mapToEntity).toList();

            mapped.setAddresses(addressesEntity);
        }

        return mapped;
    }

    public static Customer mapToGraphqlEntity(CustomerDto original) {
        var mapped = new Customer();
        var mappedAddress = Optional.ofNullable(original.getAddresses())
                .orElse(Collections.emptyList())
                .stream().map(AddressMapper::mapToGraphqlEntity)
                .toList();
//        var mappedDocuments = Optional.ofNullable(original.getDocuments())
//                .orElse(Collections.emptyList())
//                .stream().map(DocumentMapper::mapToGraphqlEntity)
//                .toList();
//        var mappedSalesOrders = Optional.ofNullable(original.getSalesOrders())
//                .orElse(Collections.emptyList())
//                .stream().map(SalesOrderMapper::mapToGraphqlEntity)
//                .toList();

        mapped.setAddresses(mappedAddress);
//        mapped.setDocuments(mappedDocuments);
//        mapped.setSalesOrders(mappedSalesOrders);
        mapped.setUuid(original.getUuid().toString());
        mapped.setEmail(original.getEmail());
        mapped.setPhone(original.getPhone());
        mapped.setFullName(original.getFullName());
        mapped.setBirthDate(original.getBirthDate());

        return mapped;
    }

}
