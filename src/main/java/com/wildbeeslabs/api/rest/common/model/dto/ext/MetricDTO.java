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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.springframework.boot.actuate.metrics.Metric;

/**
 *
 * MetricDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "metric")
public class MetricDTO<T extends Number> extends Metric<T> implements Serializable {

    @JacksonXmlProperty(localName = "timestamp")
    @JsonProperty("timestamp")
    private Date timestamp;

    @JacksonXmlProperty(localName = "request")
    @JsonProperty("request")
    private String request;

    @JacksonXmlProperty(localName = "status")
    @JsonProperty("status")
    private Integer statusCode;

    public MetricDTO() {
        super(null, null, null);
    }

    public MetricDTO(final String name, final T value, final Date timestamp) {
        super(name, value, timestamp);
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
//
//    public String getTimestamp() {
//        return (Objects.nonNull(this.timestamp)) ? DateUtils.dateToStr(this.timestamp) : null;
//    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(final String request) {
        this.request = request;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int hashCode() {
        //int hash = 3;
        int hash = super.hashCode();
        hash = 23 * hash + Objects.hashCode(this.timestamp);
        hash = 23 * hash + Objects.hashCode(this.request);
        hash = 23 * hash + Objects.hashCode(this.statusCode);
        return hash;
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (null == obj || obj.getClass() != this.getClass()) {
//            return false;
//        }
        if (!super.equals(obj)) {
            return false;
        }
        final MetricDTO other = (MetricDTO) obj;
        if (!Objects.equals(this.request, other.request)) {
            return false;
        }
        if (!Objects.equals(this.statusCode, other.statusCode)) {
            return false;
        }
        return Objects.equals(this.timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return String.format("MetricDTO {timestamp: %s, request: %s, status: %d}", this.timestamp, this.request, this.statusCode);
    }
}
