/*****************************************************************************
 * Copyright (C) Guantanamo Organization. All rights reserved.               *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Aslak Hellesoy                                           *
 * Idea by Chris Stevenson                                                   *
 *****************************************************************************/
package org.codehaus.guantanamo.testdata;

import junit.framework.TestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
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
