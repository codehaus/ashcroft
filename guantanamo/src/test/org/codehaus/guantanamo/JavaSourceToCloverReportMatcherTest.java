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
package org.codehaus.guantanamo;

import junit.framework.TestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaSourceToCloverReportMatcherTest extends TestCase {
    public void testShouldGetCoveragePathForSourcePath() {
        JavaSourceToCloverReportMatcher matcher = new JavaSourceToCloverReportMatcher("a/clover/report/dir");
        assertEquals("a/clover/report/dir/foo/bar/Zap.html", matcher.getCoveragePath("foo/bar/Zap.java"));
    }
}
