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
package com.wildbeeslabs.api.rest.common.service.ext;

import com.wildbeeslabs.api.rest.common.service.interfaces.ext.IMetricService;
import com.wildbeeslabs.api.rest.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * Metric REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Service("metricCommonService")
public class MetricServiceImpl implements IMetricService {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Default metrics counter status prefix
     */
    private static final String DEFAULT_METRIC_STATUS_PREFIX = "counter.status.";

    @Autowired
    private MetricReaderPublicMetrics publicMetrics;

    //private final List<List<Integer>> statusMetricsByMinute = new ArrayList<>();
    private final List<ConcurrentMap<String, Integer>> statusMetricsList = new ArrayList<>();
    private final ConcurrentMap<String, Integer> statusMetricsMap = new ConcurrentHashMap<>();
//    private final ConcurrentMap<String, ConcurrentMap<String, Integer>> metricMap = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        ConcurrentMap<String, Integer> lastMinuteStatus = this.initStatus();
        //filter((counterMetric) -> counterMetric.getName().contains(DEFAULT_STATUS_PREFIX))
        this.publicMetrics.metrics().stream().forEach((counterMetric) -> {
            updateMetrics(counterMetric, lastMinuteStatus);
        });
        //this.statusMetricsByMinute.add(new ArrayList(lastMinuteStatus.values()));
        this.statusMetricsList.add(lastMinuteStatus);
    }

    private ConcurrentMap<String, Integer> initStatus() {
        ConcurrentMap<String, Integer> counterList = new ConcurrentHashMap<>(this.statusMetricsMap.size());
        this.statusMetricsMap.keySet().forEach((key) -> {
            counterList.put(key, 0);
        });
        return counterList;
    }

    private void updateMetrics(final Metric<?> counterMetric, final ConcurrentMap<String, Integer> statusCount) {
        if (counterMetric.getName().contains(DEFAULT_METRIC_STATUS_PREFIX)) {
            String status = counterMetric.getName().substring(0, DEFAULT_METRIC_STATUS_PREFIX.length() + 3);
            this.statusMetricsMap.putIfAbsent(status, 0);
            statusCount.merge(status, 0, (value, newValue) -> value + counterMetric.getValue().intValue());
        }
    }

    @Override
    public void increaseCount(final String request, final int status) {
//        ConcurrentMap<String, Integer> statusMap = metricMap.getOrDefault(request, new ConcurrentHashMap<>());
//        statusMap.merge(DEFAULT_METRIC_STATUS_PREFIX + status, 1, (value, newValue) -> value + newValue);
//        this.metricMap.put(request, statusMap);

        this.statusMetricsMap.merge(DEFAULT_METRIC_STATUS_PREFIX + status, 1, (value, newValue) -> value + newValue);
    }

    @Override
    public List<ConcurrentMap<String, Integer>> getData() {
        return this.statusMetricsList;
    }

    @Override
    public Object[][] getGraphData() {
        Date current = new Date();
        int colCount = this.statusMetricsMap.size() + 1;
        int rowCount = this.statusMetricsList.size() + 1;
        Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";

        int j = 1;
        for (String status : this.statusMetricsMap.keySet()) {
            result[0][j++] = status;
        }

        for (int i = 1; i < rowCount; i++) {
            result[i][0] = DateUtils.dateToStr(new Date(current.getTime() - (60000 * (rowCount - i))));
        }

        List<Integer> minuteOfStatuses;
        List<Integer> last = new ArrayList<>();
        for (int i = 1; i < rowCount; i++) {
            minuteOfStatuses = this.statusMetricsList.get(i - 1).values().stream().collect(Collectors.toList());
            for (j = 1; j <= minuteOfStatuses.size(); j++) {
                result[i][j] = minuteOfStatuses.get(j - 1) - (last.size() >= j ? last.get(j - 1) : 0);
            }
            while (j < colCount) {
                result[i][j++] = 0;
            }
            last = minuteOfStatuses;
        }
        return result;
    }
}
