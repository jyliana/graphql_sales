package com.example.sales.mapper;

import com.example.graphql.types.Loan;
import com.example.graphql.types.LoanInput;
import com.example.sales.datasource.entity.LoanDto;

public class LoanMapper {

  private LoanMapper() {
  }

  public static LoanDto mapToEntity(LoanInput original) {
    var mapped = new LoanDto();

    mapped.setContactPersonEmail(original.getContactPersonEmail());
    mapped.setContactPersonName(original.getContactPersonName());
    mapped.setContactPersonPhone(original.getContactPersonPhone());
    mapped.setFinanceCompany(original.getFinanceCompany());

    return mapped;
  }

  public static Loan mapToGraphqlEntity(LoanDto original) {
    var mapped = new Loan();

    mapped.setUuid(original.getUuid().toString());
    mapped.setFinanceCompany(original.getFinanceCompany());
    mapped.setContactPersonEmail(original.getContactPersonEmail());
    mapped.setContactPersonName(original.getContactPersonName());
    mapped.setContactPersonPhone(original.getContactPersonPhone());

    return mapped;
  }

}
