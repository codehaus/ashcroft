package org.codehaus.guantanamo.testdata;

public class PoorlyTested {
    public void methodWithUncoveredSimpleIfs() {
        boolean b = true;
        if(b) {
            int i = 1;
            if(i == 0) {
                new Object();
            }
        }
    }

    public void methodWithStatementInIf(String s) {
		if(s.trim().equals("a")) {
			new Object();
		}
	}
}
