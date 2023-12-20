package com.example.sales.service.command;

import com.example.graphql.types.AddAddressInput;
import com.example.graphql.types.AddCustomerInput;
import com.example.graphql.types.UpdateCustomerInput;
import com.example.sales.datasource.entity.CustomerDocument;
import com.example.sales.datasource.entity.CustomerDto;
import com.example.sales.datasource.repository.CustomerRepository;
import com.example.sales.mapper.AddressMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.sales.mapper.CustomerMapper.mapToEntity;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@AllArgsConstructor
public class CustomerCommandService {

  private CustomerRepository customerRepository;

  public CustomerDto addNewCustomer(AddCustomerInput input) {
    var customerEntity = mapToEntity(input);

    return customerRepository.save(customerEntity);
  }

  public CustomerDto addAddressesToExistingCustomer(CustomerDto customer, List<AddAddressInput> addressList) {
    var addressesEntity = addressList.stream().map(AddressMapper::mapToEntity).toList();
    customer.getAddresses().addAll(addressesEntity);

    return customerRepository.save(customer);
  }

  public CustomerDto addDocumentToExistingCustomer(CustomerDto customer, String documentType, MultipartFile documentFile) {
    var documentEntity = new CustomerDocument();

    // pretend process upload, e.g. to S3 bucket or other storage
    var uploadedDocumentPath = String.format("%s/%s/%s-%s-%s",
            "https://dummy-storage.com", customer.getUuid(), documentType,
            RandomStringUtils.randomAlphabetic(6).toLowerCase(),
            documentFile.getOriginalFilename());

    documentEntity.setDocumentPath(uploadedDocumentPath);
    documentEntity.setDocumentType(documentType);

    customer.getDocuments().add(documentEntity);

    return customerRepository.save(customer);
  }

  public CustomerDto updateCustomer(CustomerDto customer, UpdateCustomerInput customerUpdate) {
    if (isNotBlank(customerUpdate.getEmail())) {
      customer.setEmail(customerUpdate.getEmail());
    }

    if (isNotBlank(customerUpdate.getPhone())) {
      customer.setPhone(customerUpdate.getPhone());
    }

    return customerRepository.save(customer);
  }

}
