package org.codehaus.guantanamo.testdata;

import junit.framework.TestCase;

public class PoorlyTestedTest extends TestCase {
    private PoorlyTested poorlyTested = new PoorlyTested();

    public void testShouldHaveSimpleIfsRemoved() {
        poorlyTested.methodWithUncoveredSimpleIfs();
    }

    public void testShouldKeepCoveredIfsWithStatements() {
        poorlyTested.methodWithStatementInIf("a");
        poorlyTested.methodWithStatementInIf("b");
    }
}
