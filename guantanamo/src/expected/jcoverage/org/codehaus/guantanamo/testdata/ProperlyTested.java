package org.codehaus.guantanamo.testdata;

public class ProperlyTested {
    public int doit(Object o) {
        try {
            return Integer.parseInt(o.toString());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException();
        }
    }
}
