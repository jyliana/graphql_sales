package com.example.sales.dataloader;

import com.example.graphql.types.SimpleModel;
import com.example.sales.constant.DataLoaderConstants;
import com.example.sales.service.query.ProductQueryService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@DgsDataLoader(name = "productDataLoader")
public class ProductDataLoader implements MappedBatchLoader<String, SimpleModel> {

  private final ProductQueryService productQueryService;

  @Qualifier(DataLoaderConstants.THREAD_POOL_EXECUTOR_NAME)
  private final Executor dataLoaderThreadPoolExecutor;

  public ProductDataLoader(ProductQueryService productQueryService, Executor dataLoaderThreadPoolExecutor) {
    this.productQueryService = productQueryService;
    this.dataLoaderThreadPoolExecutor = dataLoaderThreadPoolExecutor;
  }

  @Override
  public CompletionStage<Map<String, SimpleModel>> load(Set<String> keys) {
    return CompletableFuture.supplyAsync(
            () -> productQueryService.loadSimpleModels(keys),
            dataLoaderThreadPoolExecutor
    );
  }
}
