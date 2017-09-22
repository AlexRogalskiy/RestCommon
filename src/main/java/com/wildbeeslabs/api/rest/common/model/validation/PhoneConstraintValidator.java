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
package com.wildbeeslabs.api.rest.common.model.validation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * Phone constraint validation implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

    public static final String DEFAULT_FORMAT = "\\+[0-9]+";

    @Override
    public void initialize(final Phone phone) {
    }

    @Override
    public boolean isValid(final String phoneField, final ConstraintValidatorContext cxt) {
        if (Objects.isNull(phoneField)) {
            return true;
        }
        boolean isValid = phoneField.matches(DEFAULT_FORMAT);
        if (!isValid) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(String.format("ERROR: incorrect phone={%s} (expected format={%s})", phoneField, PhoneConstraintValidator.DEFAULT_FORMAT)).addConstraintViolation();
        }
        return isValid;
    }
}
