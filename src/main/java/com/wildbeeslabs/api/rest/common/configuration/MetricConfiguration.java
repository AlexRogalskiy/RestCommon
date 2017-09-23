/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.api.rest.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.aggregate.AggregateMetricReader;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * Metric Configuration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Configuration
@EnableAutoConfiguration
//@ConditionalOnMissingBean
//@ConditionalOnBean
public class MetricConfiguration {

//    @Autowired
//    private MetricExportProperties export;

//    @Bean
//    @ExportMetricWriter
//    public MetricWriter metricWriter(MetricExportProperties export) {
//        return new RedisMetricRepository(connectionFactory, export.getRedis().getPrefix(), export.getRedis().getKey());
//    }

//    @Bean
//    public PublicMetrics metricsAggregate() {
//        return new MetricReaderPublicMetrics(aggregatesMetricReader());
//    }

//    private MetricReader globalMetricsForAggregation() {
//        return new RedisMetricRepository(this.connectionFactory, this.export.getRedis().getAggregatePrefix(), this.export.getRedis().getKey());
//    }

//    //@ExportMetricReader
//    private MetricReader aggregatesMetricReader() {
//        AggregateMetricReader repository = new AggregateMetricReader(globalMetricsForAggregation());
//        return repository;
//    }
}
