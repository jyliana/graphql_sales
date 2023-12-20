package com.example.sales.resolver;

import com.example.graphql.DgsConstants;
import com.example.graphql.types.SalesOrderItem;
import com.example.graphql.types.SimpleModel;
import com.example.sales.dataloader.ProductDataLoader;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
public class ProductResolver {

  @DgsData(
          parentType = DgsConstants.SALESORDERITEM.TYPE_NAME,
          field = DgsConstants.SALESORDERITEM.ModelDetail
  )
  public CompletableFuture<SimpleModel> loadSimpleModels(DgsDataFetchingEnvironment env) {
    DataLoader<String, SimpleModel> productDataLoader = env.getDataLoader(ProductDataLoader.class);
    SalesOrderItem salesOrderItem = env.getSource();

    return productDataLoader.load(salesOrderItem.getModelUuid());
  }
}
