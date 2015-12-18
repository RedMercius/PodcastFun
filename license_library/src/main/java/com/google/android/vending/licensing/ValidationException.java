/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.google.android.vending.licensing;

/**
 * Indicates that an error occurred while validating the integrity of data managed by an
 * {@link Obfuscator}.}
 */
public class ValidationException extends Exception {
    public ValidationException() {
      super();
    }

    public ValidationException(String s) {
      super(s);
    }

    private static final long serialVersionUID = 1L;
}
