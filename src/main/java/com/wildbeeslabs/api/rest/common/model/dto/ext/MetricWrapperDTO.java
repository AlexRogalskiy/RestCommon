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
package com.wildbeeslabs.api.rest.common.model.dto.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * MetricWrapperDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <E>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "metrics")
public class MetricWrapperDTO<E extends MetricDTO> implements Serializable {

//    @JsonProperty("metrics")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "metric")
//    private final ConcurrentMap<String, ConcurrentMap<String, Integer>> metricsMap = new ConcurrentHashMap<>();
    @JsonProperty("metrics")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "metric")
    private final Queue<E> metrics = new ConcurrentLinkedQueue<>();

    public Queue<? extends E> getMetrics() {
        return metrics;
    }

    public void setMetrics(final List<? extends E> items) {
        this.metrics.clear();
        if (Objects.nonNull(metrics)) {
            this.metrics.addAll(metrics);
        }
    }

    public void addMetric(final E metricDto) {
        if (Objects.nonNull(metricDto)) {
            this.metrics.add(metricDto);
        }
    }

//    public ConcurrentMap<String, ConcurrentMap<String, Integer>> getMetricsMap() {
//        return metricsMap;
//    }
//
//    public void setMetricsMap(final ConcurrentMap<String, ConcurrentMap<String, Integer>> metricsMap) {
//        this.metricsMap.clear();
//        if (Objects.nonNull(metricsMap)) {
//            this.metricsMap.putAll(metricsMap);
//        }
//    }
//
//    public void addMetrics(final String key, final ConcurrentMap<String, Integer> value) {
//        this.metricsMap.put(key, value);
//    }
}
