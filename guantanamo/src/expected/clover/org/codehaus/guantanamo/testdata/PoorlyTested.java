package org.codehaus.guantanamo.testdata;

public class PoorlyTested {
    public void methodWithUncoveredSimpleIfs() {
        boolean b = true;
        int i = 1;
    }

    public void methodWithStatementInIf(String s) {
		if(s.trim().equals("a")) {
			new Object();
		}
	}
}
