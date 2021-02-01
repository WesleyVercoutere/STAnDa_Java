package com.gilbos.standa.config;

import com.gilbos.standa.business.ErrorOutput;
import com.gilbos.standa.business.SelectedFilters;
import com.gilbos.standa.business.Settings;
import com.gilbos.standa.repository.FlowErrorRepository;
import com.gilbos.standa.repository.impl.FlowErrorRepoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public SelectedFilters selectedFilters() {
        return new SelectedFilters();
    }

    @Bean
    public Settings settings() {
        return new Settings();
    }

    @Bean
    public ErrorOutput errorOutput() {
        return new ErrorOutput();
    }

    @Bean
    public FlowErrorRepository flowErrorRepository() {
        return new FlowErrorRepoImpl();
    }

    @Bean
    public FlowErrorRepository flowStopRepository() {
        return new FlowErrorRepoImpl();
    }

}
