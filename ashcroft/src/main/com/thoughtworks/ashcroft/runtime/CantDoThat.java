package com.thoughtworks.ashcroft.runtime;

/**
 * @author Obie Fernandez obie@thoughtworks.com
 * @author Aslak Helles&oslash;y
 */
public class CantDoThat extends Error {
    private final String stackTrace;

    public CantDoThat(String message, String stackTrace) {
        super(message);
        this.stackTrace = stackTrace;
    }

    public String getStackTraceAsString() {
        return stackTrace;
    }
}
