package com.thoughtworks.ashcroft.runtime;

/**
 * @author Obie Fernandez obie@thoughtworks.com
 * @author Aslak Helles&oslash;y
 */
public class CantDoThat extends Error {
    public CantDoThat(String message) {
        super(message);
    }
}
