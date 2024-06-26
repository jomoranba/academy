package com.mitocode.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
@RequiredArgsConstructor
public class MongoConfig implements InitializingBean {

  @Lazy
  private final MappingMongoConverter mappingMongoConverter;


  @Override
  public void afterPropertiesSet() throws Exception {
    mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
  }
}
