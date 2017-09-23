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
package com.wildbeeslabs.api.rest.common.controller.proxy.ext;

import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;
import com.wildbeeslabs.api.rest.common.service.interfaces.ext.IMetricService;
import com.wildbeeslabs.api.rest.common.utils.ResourceUtils;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Metric Proxy Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Component("metricCommonProxyController")
public class MetricProxyController {

    @Autowired
    private IMetricService metricService;
    @Autowired
    private ResourceUtils resourceUtils;

    public Object[][] getGraphData() {
        Object[][] items = metricService.getGraphData();
        return items;
    }

    public List<ConcurrentMap<String, Integer>> getData() throws EmptyContentException {
        List<ConcurrentMap<String, Integer>> items = metricService.getData();
        if (items.isEmpty()) {
            throw new EmptyContentException(resourceUtils.formatMessage("error.no.content"));
        }
        return items;
    }
}
