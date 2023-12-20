package com.example.sales.service.query;

import com.example.graphql.types.SimpleModel;
import com.example.sales.client.ProductGraphQLClient;
import com.example.sales.constant.ProductConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductQueryService {

  private ProductGraphQLClient productGraphQLClient;
  private ObjectMapper objectMapper;
//  private ProductRestClient productRestClient;

  public Map<String, SimpleModel> loadSimpleModels(Set<String> modelUuids) {
    var variablesMap = Map.ofEntries(
            Map.entry(ProductConstants.VARIABLE_NAME_MODEL_UUIDS, modelUuids));

    var simpleModelsGraphQLResponse = productGraphQLClient.fetchGraphQLResponse(
            ProductConstants.QUERY_SIMPLE_MODELS,
            ProductConstants.OPERATION_NAME_SIMPLE_MODELS,
            variablesMap);

    try {
      var jsonNode = objectMapper.readTree(simpleModelsGraphQLResponse.getJson());
      var simpleModelsJson = jsonNode.get("data").get("simpleModels").toString();
      var listSimpleModels = objectMapper.readValue(
              simpleModelsJson,
              new TypeReference<List<SimpleModel>>() {
              });

      return Maps.uniqueIndex(listSimpleModels, SimpleModel::getUuid);
    } catch (Exception e) {
      return Collections.emptyMap();
    }
  }

//  public Map<String, SimpleModel> loadSimpleModelsRest(Set<String> modelUuids) {
//    var listSimpleModels = productRestClient.fetchRestSimpleModels(modelUuids);
//
//    return Maps.uniqueIndex(listSimpleModels, SimpleModel::getUuid);
//  }

}
