package com.qboot.datasource.config;

import com.qboot.datasource.jooq.PageSelector;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.jooq.autoconfigure.JooqAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = JooqAutoConfiguration.class)
@ConditionalOnClass(DSLContext.class)
@ConditionalOnSingleCandidate(DSLContext.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    PageSelector pageExecutor(DSLContext dslContext) {
        dslContext.selectOne().fetch();
        return new PageSelector(dslContext);
    }

}
