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
package com.wildbeeslabs.api.rest.common.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * PropertiesUtils implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
//@ImportResource("classpath:jobs.properties")
//@PropertySource({"classpath:jobs.properties"})
public final class PropertiesUtils extends PropertyPlaceholderConfigurer implements Serializable {

    private static Map<String, String> propsMap;

    /**
     * Instantiate and load set of properties
     *
     * @param beanFactory - bean factory
     * @param props - initial set of properties
     * @throws BeansException
     */
    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactory, final Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        propsMap = new HashMap<>();
        props.keySet().stream().map((key) -> key.toString()).forEachOrdered((keyStr) -> {
            propsMap.put(keyStr, props.getProperty(keyStr));
        });
    }

    /**
     * Get the property
     *
     * @param name - property name
     * @return the property value
     */
    public static String getProperty(final String name) {
        return propsMap.get(name);
    }
}
