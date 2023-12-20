package com.example.sales.config;

import com.example.sales.constant.DataLoaderConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class DataLoaderConfig {

  @Primary
  @Bean(name = DataLoaderConstants.THREAD_POOL_EXECUTOR_NAME)
  Executor dataLoaderThreadPoolExecutor() {
    return Executors.newCachedThreadPool();
  }
}
