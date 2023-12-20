package com.example.sales.mapper;

import com.example.graphql.types.LoanInput;
import com.example.sales.datasource.entity.LoanDto;

public class LoanMapper {
  public static LoanDto mapToEntity(LoanInput original) {
    var mapped = new LoanDto();

    mapped.setContactPersonEmail(original.getContactPersonEmail());
    mapped.setContactPersonName(original.getContactPersonName());
    mapped.setContactPersonPhone(original.getContactPersonPhone());
    mapped.setFinanceCompany(original.getFinanceCompany());

    return mapped;
  }


}
