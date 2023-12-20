package com.example.sales.mapper;

import com.example.graphql.types.FinanceInput;
import com.example.sales.datasource.entity.FinanceDto;

public class FinanceMapper {

  public static FinanceDto mapToEntity(FinanceInput original) {
    var mapped = new FinanceDto();

    mapped.setBaseAmount(original.getBaseAmount());
    mapped.setLoan(original.getIsLoan());
    mapped.setDiscountAmount(original.getDiscountAmount());
    mapped.setTaxAmount(original.getTaxAmount());

    if (original.getLoan() != null) {
      var mappedLoan = LoanMapper.mapToEntity(original.getLoan());

      mappedLoan.setFinanceDto(mapped);
      mapped.setLoanDto(mappedLoan);
    }

    return mapped;
  }
}
